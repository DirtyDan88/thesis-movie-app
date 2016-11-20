package net.dirtydan.thesis.person.endpoint;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import net.dirtydan.thesis.person.service.integration.WikipediaClient;




@RestController
@Service // needed for hystrix
public class PersonServiceEndpoint {
  
  final Logger LOG = Logger.getLogger(this.getClass().getName());
  
  @Autowired
  WikipediaClient wikiClient;
  
  
  
  @RequestMapping(method = RequestMethod.GET, value = "/person")
  @HystrixCommand(fallbackMethod = "fallback")
  public ResponseEntity<String> findPersonByName(
                                  @RequestParam(value = "name") String name
                                )
  {
    LOG.info(String.format("%s.findMovies() invoked with name='%s'",
                           this.getClass().getName(), name));
    
    name = name.replace(" ", "_");
    ResponseEntity<String> response = wikiClient.getPersonInfo(name);
    
    String text = parseWikiResponse(response.getBody());
    return text == null ?
            new ResponseEntity<>(HttpStatus.NOT_FOUND) :
            new ResponseEntity<>(text, HttpStatus.OK);
  }
  
  
  
  private String parseWikiResponse(String jsonString) {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode rootNode;
    
    try {
      rootNode = objectMapper.readTree(jsonString);
      return rootNode.findValue("extract").asText();
    } catch (IOException e) {}
    
    return null;
  }
  
  public ResponseEntity<String> fallback(String name) {
    LOG.info(String.format("%s.findMovies() deliver fallback value",
                           this.getClass().getName()));
    return new ResponseEntity<>(name + " not found", HttpStatus.OK);
  }
  
}