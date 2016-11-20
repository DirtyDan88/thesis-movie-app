package net.dirtydan.thesis.movie.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import net.dirtydan.thesis.movie.model.GenericBuilder;
import net.dirtydan.thesis.movie.model.Movie;
import net.dirtydan.thesis.movie.persistence.MovieRepository;
import net.dirtydan.thesis.movie.service.integration.OMDbClient;
import net.dirtydan.thesis.movie.service.integration.OMDbMovieInfo;



@Service
public class MovieService {
  
  @Autowired
  MovieRepository movieRepository;
  
  @Autowired
  OMDbClient omdbClient;
  
  
  
  public Collection<Movie> findMovies(String title, long year, String genre)
                                        throws Exception {
    // get basic movie data from own database
    Collection<Movie> movies = movieRepository.findByTitle(title);
    
    if (year != -1) {
      movies = movies.stream()
                 .filter(movie -> movie.getYear() == year)
                 .collect(Collectors.toList());
    }
    
    if (!genre.isEmpty()) {
      movies = movies.stream()
                 .filter(movie -> movie.getGenre().contains(genre))
                 .collect(Collectors.toList());
    }
    
    // enrich movie info with data from OMDbapi.com
    movies.parallelStream()
      .forEach(movie -> movie.mergeOMDbMovieInfo(
                          getOMDbMovieInfo(movie.getTitle())
                        )
       );
    
    return movies;
  }
  
  
  
  @HystrixCommand(fallbackMethod = "fallback")
  private OMDbMovieInfo getOMDbMovieInfo(String title) {
    ResponseEntity<OMDbMovieInfo> response = omdbClient.getMovieInfo(title);
    
    if (response.getStatusCode() != HttpStatus.OK ||
        response.getBody().getTitle() == null)
      return fallback(title);
    
    return response.getBody();
  }
  
  public OMDbMovieInfo fallback(String title) {
    return GenericBuilder.of(OMDbMovieInfo::new)
             .with(OMDbMovieInfo::directors,  "-")
             .with(OMDbMovieInfo::actors,     "-")
             .with(OMDbMovieInfo::writers,    "-")
             .with(OMDbMovieInfo::plot,       "-")
             .with(OMDbMovieInfo::poster,     "-")
             .with(OMDbMovieInfo::imdbRating, "-")
             .build();
  }
  
  
}
