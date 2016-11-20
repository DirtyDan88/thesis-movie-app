package net.dirtydan.thesis.hystrix.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@SpringBootApplication
@Controller
@EnableHystrixDashboard
public class NetflixHystrixDashboardApplication {
  
  public static void main(String[] args) {
    SpringApplication.run(NetflixHystrixDashboardApplication.class, args);
  }
  
  @RequestMapping("/")
  public String home() {
    return "forward:/hystrix";
  }
  
}
