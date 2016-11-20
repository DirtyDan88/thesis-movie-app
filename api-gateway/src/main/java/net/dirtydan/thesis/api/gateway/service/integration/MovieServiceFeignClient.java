package net.dirtydan.thesis.api.gateway.service.integration;

import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import net.dirtydan.thesis.api.gateway.model.GenericBuilder;
import net.dirtydan.thesis.api.gateway.model.Movie;



@Component("movie-feign-client")
public class MovieServiceFeignClient implements MovieServiceClient {
  
  final Logger LOG = Logger.getLogger(this.getClass().getName());
  
  @Autowired
  MovieServiceFeignInterface feignClient;
  
  
  
  @Override
  @HystrixCommand(fallbackMethod = "fallback")
  public Collection<Movie> findMovies(String title, long year, String genre)
      throws Exception {
    LOG.info(String.format("%s.findMovies() invoked with title='%s' year='%d' genre='%s'",
                           this.getClass().getName(), title, year, genre));
    
    ResponseEntity<Collection<Movie>> response = feignClient.findMovies(title, year, genre);
    
    return response.getBody();
  }
  
  public Collection<Movie> fallback(String title, long year, String genre) {
    LOG.info(String.format("%s.findMovies() fallback method invoked",
                           this.getClass().getName()));
    return Arrays.asList(
             GenericBuilder.of(Movie::new)
               .with(Movie::id, (long)-1)
               .build()
           );
  }
  
}



@FeignClient(value = "movie-service")
interface MovieServiceFeignInterface {
  
  @RequestMapping(method = RequestMethod.GET, value = "/movies")
  ResponseEntity<Collection<Movie>> findMovies(
    @RequestParam(value = "title") String title,
    @RequestParam(value = "year") long year,
    @RequestParam(value = "genre") String genre
  );
}

