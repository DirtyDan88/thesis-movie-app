package net.dirtydan.thesis.api.gateway.service.integration;

import java.util.Collection;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.VertxFeign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

import io.vertx.core.Future;
import io.vertx.core.Vertx;

import net.dirtydan.thesis.api.gateway.model.Movie;



@Component("movie-feign-vertx-client")
public class MovieServiceFeignVertxClient implements MovieServiceClient {
  
  final Logger LOG = Logger.getLogger(this.getClass().getName());
  
  @Autowired
  private LoadBalancerClient loadBalancedClient;
  
  
  
  @Override
  public Collection<Movie> findMovies(String title, long year, String genre)
      throws Exception {
    LOG.info(String.format("%s.findMovies() invoked with title='%s' year='%d' genre='%s'",
                           this.getClass().getName(), title, year, genre));
    Vertx vertx = Vertx.vertx();
    
    MovieServiceFeignVertxInterface movieservice = VertxFeign
      .builder()
      .vertx(vertx)
      .encoder(new JacksonEncoder())
      .decoder(new JacksonDecoder())
      .target(
        MovieServiceFeignVertxInterface.class,
        loadBalancedClient.choose("movie-service").getUri().toString()
      );
    
    
    movieservice.findMovies(title, year, genre).setHandler(
        res -> {
          if (res.succeeded()) {
            res.result().stream()
              .map(movie -> movie.title)
              .forEach(System.out::println);
          }
      }
    );
    
    return null;
  }
  
  
  
//@Override
//public Collection<Movie> findMovies(String title, long year, String genre)
//    throws Exception {
//  LOG.info(String.format("%s.findMovies()-vertx invoked with title='%s' year='%d' genre='%s'",
//                         this.getClass().getName(), title, year, genre));
//
//  Vertx vertx = Vertx.vertx();
//  CircuitBreaker breaker = CircuitBreaker.create("movie-circuit-breaker", vertx,
//    new CircuitBreakerOptions()
//      .setMaxFailures(5) // number of failure before opening the circuit
//      .setTimeout(2000) // consider a failure if the operation does not succeed in time
//      .setFallbackOnFailure(true) // do we call the fallback on failure
//      .setResetTimeout(10000) // time spent in open state before attempting to re-try
//  );
//  
//  
//  //breaker.<Collection<Movie>>executeWithFallback(
//  Future<String> f = breaker.executeWithFallback(
//    future -> {
//      vertx.createHttpClient().getNow(8090, "localhost", "/movies", response -> {
//        if (response.statusCode() != 200) {
//          future.fail("HTTP error");
//        } else {
//          response
//            .exceptionHandler(future::fail)
//            .bodyHandler(buffer -> {
//              future.complete( buffer.toString() );
//            });
//        }
//      });
//    }, v -> { // Executed when the circuit is opened
//      return "asdads"; //fallback();
//    }//)
////    .setHandler(ar -> { // Do something with the result
////  //    res =  ar.result();
////    }
//  );
//  
//  
//  //String res;
//  f.setHandler(ar -> {
//    System.out.println("Result: " + ar.result());
//    //res = ar.result();
//    
//  });
//  
//  //Collection<Movie> res; // =  f.result();
//  
//  System.out.println("");
//  
//  
//  return fallback();
//}
//
//private Collection<Movie> fallback() {
//  LOG.info(String.format("%s.executeWithFallback()-vertx fallback method invoked",
//                         this.getClass().getName()));
//  return Arrays.asList(
//           GenericBuilder.of(Movie::new)
//            .with(Movie::id, (long)-1)
//            .build()
//  );
//}
  
}



@Headers({"Accept: application/json"})
interface MovieServiceFeignVertxInterface {
  
  @RequestLine("GET /movies")
  Future<Collection<Movie>> findMovies(
                              @Param("title") String title,
                              @Param("year") long year,
                              @Param("genre") String genre
                            );
}
