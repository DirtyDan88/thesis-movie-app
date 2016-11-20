package net.dirtydan.thesis.api.gateway.service;

import java.util.Collection;
import java.util.ListIterator;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.dirtydan.thesis.api.gateway.model.Movie;
import net.dirtydan.thesis.api.gateway.service.integration.MovieServiceClient;
import net.dirtydan.thesis.api.gateway.service.integration.PersonServiceClient;



@Service
public class ApiGatewayService {
  
  @Resource(name = "movie-${service.integration.movie-client}")
  MovieServiceClient movieservice;
  
  @Resource(name = "person-${service.integration.person-client}")
  PersonServiceClient personservice;
  
  
  
  public Collection<Movie> getMovieData(String title, long year, String genre,
                                        boolean actorInfo, boolean directorInfo)
                                          throws Exception {
    // request movie data
    Collection<Movie> movies = movieservice.findMovies(title, year, genre);
    
    // request person data
    if (actorInfo) {
      movies.parallelStream().forEach(
          movie -> {
            for (final ListIterator<String> actor = movie.actors.listIterator();
                                            actor.hasNext();) {
              final String name = actor.next();
              actor.set(addPersonInfo(name));
            }
          }
        );
    }
    if (directorInfo) {
      movies.parallelStream().forEach(
          movie -> {
            for (final ListIterator<String> director = movie.directors.listIterator();
                                            director.hasNext();) {
              final String name = director.next();
              director.set(addPersonInfo(name));
            }
          }
        );
    }
    
    
      
//    movies.parallelStream().forEach(
//        movie -> movie.actors.forEach(
//          actor -> {
//            
//          }
//        )
//      );
    
    
    return movies;
    
  }
  
  
  private String addPersonInfo(String name) {
    
    try {
      return name + ": " + personservice.findByName(name);
      
    } catch (Exception e) {}
    
    return "ERROR here";
  }
  
}
