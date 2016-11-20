package net.dirtydan.thesis.api.gateway.service.integration;

import java.util.Collection;

import net.dirtydan.thesis.api.gateway.model.Movie;



public interface MovieServiceClient {
  
  public Collection<Movie> findMovies(
      String title, long year, String genre) throws Exception;
  
}
