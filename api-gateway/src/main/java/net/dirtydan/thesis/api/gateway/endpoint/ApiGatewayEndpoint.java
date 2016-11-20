package net.dirtydan.thesis.api.gateway.endpoint;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.dirtydan.thesis.api.gateway.model.Movie;
import net.dirtydan.thesis.api.gateway.service.ApiGatewayService;



@RestController
public class ApiGatewayEndpoint {
  
  final Logger LOG = Logger.getLogger(this.getClass().getName());
  
  @Autowired
  private ApiGatewayService service;
  
  
  
  @RequestMapping(method = RequestMethod.GET, value = "/movies")
  public ResponseEntity<Collection<?>> getMovieData(
      @RequestParam(value = "title", defaultValue = "",   required = false) String title,
      @RequestParam(value = "year",  defaultValue = "-1", required = false) long year,
      @RequestParam(value = "genre", defaultValue = "",   required = false) String genre,
      
      @RequestParam(value = "actor-info",
                      defaultValue = "false", required = false) boolean actorInfo,
      @RequestParam(value = "director-info", 
                      defaultValue = "false", required = false) boolean directorInfo,
      
      @RequestHeader(value = "zuul-pre-filtered", defaultValue = "false") boolean prefilteredByZuul,
      @RequestHeader(value = "zuul-header-info",  defaultValue = "") String zuulInfo
    )
  {
    if (prefilteredByZuul) {
      LOG.info(String.format("%s.getMovieData() zuul-header-info: %s",
                             this.getClass().getName(), zuulInfo));
    }
    
    Collection<Movie> movies;
    try {
      movies = service.getMovieData(title, year, genre, actorInfo, directorInfo);
    } catch (Exception e) {
      LOG.log(Level.SEVERE, "Exception in getMovieData()", e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    return movies.isEmpty() ?
             new ResponseEntity<>(HttpStatus.NO_CONTENT) :
             new ResponseEntity<>(movies, HttpStatus.OK);
  }
  
}
