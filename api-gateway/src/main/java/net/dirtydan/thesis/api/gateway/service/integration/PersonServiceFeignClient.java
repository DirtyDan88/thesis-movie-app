package net.dirtydan.thesis.api.gateway.service.integration;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;



@Component("person-feign-client")
public class PersonServiceFeignClient implements PersonServiceClient {
  
  final Logger LOG = Logger.getLogger(this.getClass().getName());
  
  @Autowired
  PersonServiceFeignInterface feignPersonClient;
  
  
  
  @Override
  @HystrixCommand(fallbackMethod = "fallback")
  public String findByName(String name) throws Exception {
    return feignPersonClient.findByName(name).getBody();
  }
  
  public String fallback(String name) {
    return "person-service currently unavailable";
  }
  
}



@FeignClient(value = "person-service")
interface PersonServiceFeignInterface {
  
  @RequestMapping(method = RequestMethod.GET, value = "/person")
  ResponseEntity<String> findByName(
                           @RequestParam(value = "name") String name
                         );
}