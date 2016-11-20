package net.dirtydan.thesis.api.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.stereotype.Component;



@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableAutoConfiguration
public class ApiGatewayApplication {
  
  public static void main(String[] args) {
    SpringApplication.run(ApiGatewayApplication.class, args);
  }
  
}



/**
 * Prints out the available service discovery information.
 */
@Component
class DiscoveryClientSample implements CommandLineRunner {
  
  @Autowired
  DiscoveryClient discoveryClient;
  
  @Override
  public void run(String... strings) throws Exception {
    System.out.println(discoveryClient.description());
    
    discoveryClient.getServices().stream()
      .map(service -> "Service: " + service)
      .forEach(System.out::println);
    
    discoveryClient.getInstances("movie-service").stream()
      .map(instance -> "ID:   " + instance.getServiceId()
                   + "\nHost: " + instance.getHost() + ":" + instance.getPort()
                   + "\nURI:  " + instance.getUri() + "\n")
      .forEach(System.out::println);
    
    discoveryClient.getInstances("person-service").stream()
      .map(instance -> "ID:   " + instance.getServiceId()
                 + "\nHost: " + instance.getHost() + ":" + instance.getPort()
                 + "\nURI:  " + instance.getUri() + "\n")
      .forEach(System.out::println);
  }
}

/**
 * Makes n load balancing decisions.
 */
@Component
class LoadBalancerTest implements CommandLineRunner {
  
  @Autowired
  private LoadBalancerClient loadBalancedClient;
  
  int n = 10;
  
  @Override
  public void run(String... arg0) throws Exception {
    ServiceInstance instance;
    
    for (int i=0; i<n; ++i) {
      instance = null;
      instance = loadBalancedClient.choose("movie-service");
      
      if (instance == null) {
        System.out.println("## chosen instance: NULL");
      } else {
        System.out.println("## chosen instance: " + 
              instance.getServiceId() + ":" + instance.getPort());
      }
    }
  }
}

