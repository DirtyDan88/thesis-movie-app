package net.dirtydan.thesis.api.gateway.service.integration;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import net.dirtydan.thesis.api.gateway.model.GenericBuilder;
import net.dirtydan.thesis.api.gateway.model.Movie;



@Component("movie-manuel-client")
public class MovieServiceManuelClient implements MovieServiceClient {
  
  final Logger LOG = Logger.getLogger(this.getClass().getName());
  
  @Autowired
  DiscoveryClient discoveryClient;
  
  
  
  @Override
  @HystrixCommand(
    commandProperties = {
      @HystrixProperty(
        name = "execution.isolation.strategy", value = "THREAD"),
      @HystrixProperty(
        name = "execution.isolation.thread.timeoutInMilliseconds", value = "500"),
      @HystrixProperty(
        name = "circuitBreaker.errorThresholdPercentage", value = "50")
    },
    fallbackMethod = "fallback",
    threadPoolProperties = {
      @HystrixProperty(name = "coreSize", value = "20")
    }
  )
  public Collection<Movie> findMovies(String title, long year, String genre) {
    LOG.info(String.format("%s.findMovies() invoked with title='%s' year='%d' genre='%s'",
                           this.getClass().getName(), title, year, genre));
    
    List<ServiceInstance> instances = discoveryClient.getInstances("movie-service");
    ServiceInstance instance = instances.get(new Random().nextInt(instances.size()));
    
    String url = String.format("%s/movies?title=%s&year=%d&genre=%s",
                               instance.getUri(), title, year, genre);
    
    RestTemplate restTmpl = new RestTemplate();
    ResponseEntity<Movie[]> response = restTmpl.getForEntity(url, Movie[].class);
    
    return Arrays.asList(response.getBody());
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
  
  
  
  
  
  //==========================================================================//
  /*   Performing the same request, but wrapped in a "plain" HystrixCommand   */
  
  
  
//  @Override
//  public Collection<Movie> findMovies(String title, long year, String genre)
//      throws Exception {
//    return new _findMovies(title, year, genre).execute();
//  }
//  
//  class _findMovies extends com.netflix.hystrix.HystrixCommand<Collection<Movie>> {
//    
//    String title; long year; String genre;
//    
//    public _findMovies(String title, long year, String genre) {
//      super(HystrixCommandGroupKey.Factory.asKey("movie-service-group"));
//      this.title = title;
//      this.year = year;
//      this.genre = genre;
//    }
//    
//    @Override
//    protected Collection<Movie> run() throws Exception {
//      LOG.info(String.format("%s.run() invoked with title='%s' year='%d' genre='%s'",
//                             this.getClass().getName(), title, year, genre));
//      List<ServiceInstance> instances = discoveryClient.getInstances("movie-service");
//      ServiceInstance instance = instances.get(new Random().nextInt(instances.size()));
//      
//      String url = String.format("%s/movies?title=%s&year=%d&genre=%s",
//                                 instance.getUri(), title, year, genre);
//      
//      RestTemplate restTmpl = new RestTemplate();
//      ResponseEntity<Movie[]> response = restTmpl.getForEntity(url, Movie[].class);
//      
//      return Arrays.asList(response.getBody());
//    }
//    
//    @Override
//    protected Collection<Movie> getFallback() {
//      LOG.info(String.format("%s.run() fallback method invoked",
//               this.getClass().getName()));
//      return Arrays.asList(
//               GenericBuilder.of(Movie::new)
//                .with(Movie::id, (long)-1)
//                .build()
//      );
//    }
//  }
  
}
