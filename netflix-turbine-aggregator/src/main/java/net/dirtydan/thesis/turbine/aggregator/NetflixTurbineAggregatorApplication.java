package net.dirtydan.thesis.turbine.aggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.turbine.stream.EnableTurbineStream;



@SpringBootApplication
@EnableAutoConfiguration
@EnableTurbineStream
@EnableEurekaClient
public class NetflixTurbineAggregatorApplication {
  
  public static void main(String[] args) {
    SpringApplication.run(NetflixTurbineAggregatorApplication.class, args);
  }
}
