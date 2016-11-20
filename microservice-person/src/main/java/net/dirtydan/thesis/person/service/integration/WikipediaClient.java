package net.dirtydan.thesis.person.service.integration;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



@FeignClient(name = "omdb-api", url = "${service.integration.wikipediaapi}")
public interface WikipediaClient {
  
  @RequestMapping(method = RequestMethod.GET)
  ResponseEntity<String> getPersonInfo(
                           @RequestParam(value = "titles") String title
                         );
}
