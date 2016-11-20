package net.dirtydan.thesis.movie.persistence;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import net.dirtydan.thesis.movie.model.GenericBuilder;
import net.dirtydan.thesis.movie.model.Movie;



@Repository("movie-repository")
public class MockMovieRepository implements MovieRepository {
  
  private static final LinkedList<Movie> movies = new LinkedList<>();
  
  
  
  @Override
  public Collection<Movie> findByTitle(String title) {
    if (title.isEmpty()) return movies;
    
    return movies.parallelStream()
             .filter(movie -> movie.getTitle().equals(title))
             .collect(Collectors.toList());
  }
  
  @Override
  public Collection<Movie> findByYear(long year) {
    return movies.parallelStream()
             .filter(movie -> movie.getYear() == year)
             .collect(Collectors.toList());
  }
  
  @Override
  public Collection<Movie> findByGenre(String genre) {
    return movies.parallelStream()
             .filter(movie -> movie.getGenre().contains(genre))
             .collect(Collectors.toList());
  }
  
  
  
  static {
    movies.add(
      GenericBuilder.of(Movie::new)
        .with(Movie::title, "The Big Lebowski")
        .with(Movie::year, Long.valueOf(1998))
        .with(Movie::genre, "Comedy")
        .with(Movie::genre, "Crime")
        .build()
      );
    movies.add(
        GenericBuilder.of(Movie::new)
          .with(Movie::title, "Dead Poets Society")
          .with(Movie::year, Long.valueOf(1989))
          .with(Movie::genre, "Comedy")
          .with(Movie::genre, "Drama")
          .build()
        );
    movies.add(
        GenericBuilder.of(Movie::new)
          .with(Movie::title, "Black Swan")
          .with(Movie::year, Long.valueOf(2010))
          .with(Movie::genre, "Drama")
          .with(Movie::genre, "Thriller")
          .build()
        );
    movies.add(
        GenericBuilder.of(Movie::new)
          .with(Movie::title, "The Lion King")
          .with(Movie::year, Long.valueOf(1994))
          .with(Movie::genre, "Animation")
          .with(Movie::genre, "Adventure")
          .with(Movie::genre, "Drama")
          .build()
        );
    movies.add(
        GenericBuilder.of(Movie::new)
          .with(Movie::title, "Spotlight")
          .with(Movie::year, Long.valueOf(2015))
          .with(Movie::genre, "Biography")
          .with(Movie::genre, "Crime")
          .with(Movie::genre, "Drama")
          .build()
        );
    movies.add(
        GenericBuilder.of(Movie::new)
          .with(Movie::title, "Pulp Fiction")
          .with(Movie::year, Long.valueOf(1994))
          .with(Movie::genre, "Crime")
          .with(Movie::genre, "Drama")
          .build()
        );
  }
}
