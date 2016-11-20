package net.dirtydan.thesis.movie.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import net.dirtydan.thesis.movie.service.integration.OMDbMovieInfo;



public class Movie {
  
  private static final AtomicLong nextId = new AtomicLong();
  
  private long id;
  
  private String title;
  
  private long year;
  
  private List<String> genre = new ArrayList<>();
  
  
  
  private List<String> directors = new ArrayList<>();
  
  private List<String> actors = new ArrayList<>();
  
  private List<String> writers = new ArrayList<>();
  
  private String plot;
  
  private String posterURI;
  
  private String imdbRating;
  
  
  
  public Movie() {
    this.id = nextId.getAndIncrement();
  }
  
  public long getId() {
    return id;
  }
  
  public String getTitle() {
    return title;
  }
  
  public void title(String title) {
    this.title = title;
  }
  
  public long getYear() {
    return year;
  }
  
  public void year(long year) {
    this.year = year;
  }
  
  public List<String> getGenre() {
    return genre;
  }
  
  public void genre(String genre) {
    this.genre.add(genre);
  }
  
  
  
  public void mergeOMDbMovieInfo(OMDbMovieInfo omdbMovieInfo) {
    this.directors  = omdbMovieInfo.getDirectors();
    this.actors     = omdbMovieInfo.getActors();
    this.writers    = omdbMovieInfo.getWriters();
    this.plot       = omdbMovieInfo.getPlot();
    this.posterURI  = omdbMovieInfo.getPoster();
    this.imdbRating = omdbMovieInfo.getImdbRating();
  }
  
  public List<String> getDirectors() {
    return directors;
  }
  
  public void addDirector(String director) {
    this.directors.add(director);
  }

  public List<String> getActors() {
    return actors;
  }
  
  public void addActor(String actor) {
    this.actors.add(actor);
  }
  
  public List<String> getWriters() {
    return writers;
  }
  
  public void addWriters(String writer) {
    this.writers.add(writer);
  }
  
  public String getPlot() {
    return plot;
  }
  
  public void setPlot(String plot) {
    this.plot = plot;
  }
  
  public String getPosterURI() {
    return posterURI;
  }
  
  public void setPosterURI(String posterURI) {
    this.posterURI = posterURI;
  }
  
  public String getImdbRating() {
    return imdbRating;
  }
  
  public void setImdbRating(String imdbRating) {
    this.imdbRating = imdbRating;
  }
}
