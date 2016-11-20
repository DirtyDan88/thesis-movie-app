package net.dirtydan.thesis.movie.service.integration;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



@FeignClient(name = "omdb-api", url = "${service.integration.omdbapi}")
public interface OMDbClient {
  
  @RequestMapping(method = RequestMethod.GET)
  ResponseEntity<OMDbMovieInfo> getMovieInfo(
                                  @RequestParam(value = "t") String title
                                );
}
