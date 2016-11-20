package net.dirtydan.thesis.zuul.proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;



@SpringBootApplication
@EnableZuulProxy
public class NetflixZuulProxyApplication {
  
  public static void main(String[] args) {
    SpringApplication.run(NetflixZuulProxyApplication.class, args);
  }
}