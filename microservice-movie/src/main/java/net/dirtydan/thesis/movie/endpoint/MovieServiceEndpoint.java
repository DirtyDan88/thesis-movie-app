package net.dirtydan.thesis.movie.endpoint;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.dirtydan.thesis.movie.model.Movie;
import net.dirtydan.thesis.movie.service.MovieService;



@RestController
public class MovieServiceEndpoint {
  
  final Logger LOG = Logger.getLogger(this.getClass().getName());
  
  @Autowired
  MovieService service;
  
  
  
  @RequestMapping(method = RequestMethod.GET, value = "/movies")
  public ResponseEntity<Collection<Movie>> findMovies(
      @RequestParam(value = "title", defaultValue = "",   required = false) String title,
      @RequestParam(value = "year",  defaultValue = "-1", required = false) long year,
      @RequestParam(value = "genre", defaultValue = "",   required = false) String genre
    )
  {
    LOG.info(String.format("%s.findMovies() invoked with title='%s' year='%d' genre='%s'",
                           this.getClass().getName(), title, year, genre));
    
    Collection<Movie> movies;
    try {
      movies = service.findMovies(title, year, genre);
      
    } catch (Exception e) {
      LOG.log(Level.SEVERE, "Exception in findMovies()", e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    return movies.size() > 0 ?
             new ResponseEntity<>(movies, HttpStatus.OK) :
             new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
