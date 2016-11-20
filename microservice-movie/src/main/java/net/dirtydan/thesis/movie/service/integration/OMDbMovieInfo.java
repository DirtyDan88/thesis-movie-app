package net.dirtydan.thesis.movie.service.integration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonSetter;



public class OMDbMovieInfo {
  
  private String title;
  
  private List<String> directors = new ArrayList<>();
  
  private List<String> actors = new ArrayList<>();
  
  private List<String> writers = new ArrayList<>();
  
  private String plot;
  
  private String poster;
  
  private String imdbRating;
  
  
  
  public String getTitle() {
    return title;
  }

  @JsonSetter("Title")
  public void title(String title) {
    this.title = title;
  }
  
  public List<String> getDirectors() {
    return directors;
  }

  @JsonSetter("Director")
  public void directors(String directors) {
    this.directors = Arrays.asList(directors.split(",")).stream()
                       .map(director -> director.trim())
                       .collect(Collectors.toList());
  }

  public List<String> getWriters() {
    return writers;
  }

  @JsonSetter("Writer")
  public void writers(String writers) {
    this.writers = Arrays.asList(writers.split(",")).stream()
                     .map(director -> director.trim())
                     .collect(Collectors.toList());
  }
  
  public List<String> getActors() {
    return actors;
  }

  @JsonSetter("Actors")
  public void actors(String actors) {
    this.actors = Arrays.asList(actors.split(",")).stream()
                    .map(director -> director.trim())
                    .collect(Collectors.toList());
  }

  public String getPlot() {
    return plot;
  }

  @JsonSetter("Plot")
  public void plot(String plot) {
    this.plot = plot;
  }

  public String getPoster() {
    return poster;
  }

  @JsonSetter("Poster")
  public void poster(String poster) {
    this.poster = poster;
  }

  public String getImdbRating() {
    return imdbRating;
  }

  @JsonSetter("imdbRating")
  public void imdbRating(String imdbRating) {
    this.imdbRating = imdbRating;
  }
  
}
