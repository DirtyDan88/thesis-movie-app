package net.dirtydan.thesis.admin.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

import de.codecentric.boot.admin.config.EnableAdminServer;



@SpringBootApplication
@EnableAdminServer
@Configuration
@EnableAutoConfiguration
@EnableDiscoveryClient
public class SpringCloudAdminServerApplication {
  
  public static void main(String[] args) {
    SpringApplication.run(SpringCloudAdminServerApplication.class, args);
  }
}
