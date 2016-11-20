package net.dirtydan.thesis.api.gateway.service.integration;

import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import net.dirtydan.thesis.api.gateway.model.GenericBuilder;
import net.dirtydan.thesis.api.gateway.model.Movie;



@Component("movie-loadbalanced-client")
public class MovieServiceLoadbalancedClient implements MovieServiceClient {
  
  final Logger LOG = Logger.getLogger(this.getClass().getName());
  
  @Autowired
  private LoadBalancerClient clientLoadbalancer;
  
  
  
  @Override
  @HystrixCommand(fallbackMethod = "fallback")
  public Collection<Movie> findMovies(String title, long year, String genre)
      throws Exception {
    LOG.info(String.format("%s.findMovies() invoked with title='%s' year='%d' genre='%s'",
                           this.getClass().getName(), title, year, genre));
    
    // choosing an instance and building the URL
    ServiceInstance instance = clientLoadbalancer.choose("movie-service");
    String url = String.format("%s/movies?title=%s&year=%d&genre=%s",
                               instance.getUri(), title, year, genre);
    LOG.info(String.format("%s.findMovies() load balancer has chosen: %s",
                           this.getClass().getName(), instance.getUri()));
    
    // performing the request
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
  
}
