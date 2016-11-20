package net.dirtydan.thesis.api.gateway.model;

import java.util.ArrayList;
import java.util.List;



public class Movie {
  
  public long id;
  
  public String title;
  
  public long year;
  
  public List<String> genre = new ArrayList<>();
  
  public List<String> directors = new ArrayList<>();
  
  public List<String> actors = new ArrayList<>();
  
  public List<String> writers = new ArrayList<>();
  
  public String plot;
  
  public String posterURI;
  
  public String imdbRating;
  
  
  
  public void id(long id) {
    this.id = id;
  }
  
}
