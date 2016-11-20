package net.dirtydan.thesis.api.gateway.service.integration;

import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import net.dirtydan.thesis.api.gateway.model.GenericBuilder;
import net.dirtydan.thesis.api.gateway.model.Movie;



@Component("movie-loadbalanced-rest-client")
public class MovieServiceLoadbalancedRESTClient implements MovieServiceClient {
  
  final Logger LOG = Logger.getLogger(this.getClass().getName());
  
  @LoadBalanced
  @Bean
  RestTemplate restTemplate(){ return new RestTemplate(); }
  
  @Autowired
  RestTemplate loadBalancedRESTTemplate;
  
  
  
  @Override
  @HystrixCommand(fallbackMethod = "fallback")
  public Collection<Movie> findMovies(String title, long year, String genre)
      throws Exception {
    LOG.info(String.format("%s.findMovies() invoked with title='%s' year='%d' genre='%s'",
                           this.getClass().getName(), title, year, genre));
    
    // we do not have to explicitly choose a service-instance (just use the
    // service name (spring-application-name)
    String serviceName = "http://movie-service/movies";
    String url = String.format("%s?title=%s&year=%d&genre=%s",
                               serviceName, title, year, genre);
    
    // The loadbalanced rest-template select an instance for the request
    ResponseEntity<Movie[]> response = this.loadBalancedRESTTemplate.getForEntity(
                                         url, Movie[].class
                                       );
    
    String xAppCtx = response.getHeaders().getFirst("X-Application-Context");
    LOG.info(String.format("%s.findMovies() load balancer has chosen: %s",
        this.getClass().getName(), xAppCtx));
    
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
}

