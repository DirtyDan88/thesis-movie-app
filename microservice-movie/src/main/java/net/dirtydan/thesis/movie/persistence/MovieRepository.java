package net.dirtydan.thesis.movie.persistence;

import java.util.Collection;

import net.dirtydan.thesis.movie.model.Movie;



public interface MovieRepository {
  
  public Collection<Movie> findByTitle(String title) throws Exception;
  
  public Collection<Movie> findByYear(long year) throws Exception;
  
  public Collection<Movie> findByGenre(String genre) throws Exception;
}
