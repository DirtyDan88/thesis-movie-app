package net.dirtydan.thesis.api.gateway.service.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ConfigurationBasedServerList;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;



public class MovieServiceRibbonConfiguration {
  
  @Autowired
  IClientConfig ribbonClientConfig;
  
  @Bean
  public ServerList<Server> ribbonServerList() {
    return new ConfigurationBasedServerList();
    //return new StaticServerList<>(new Server("localhost", 8080));
  }
  
  @Bean
  public IPing ribbonPing(IClientConfig config) {
    return new PingUrl();
  }
  
  @Bean
  public IRule ribbonRule(IClientConfig config) {
    // each service gets a weight according to its average response time
//    IRule rule = new WeightedResponseTimeRule();
    
    // skips servers that are deemed "circuit tripped" or with high
    // concurrent connection count
//    IRule rule = new AvailabilityFilteringRule();
    
    // round-robin loadbalancing (default)
//    IRule rule = new RoundRobinRule();
    
    // choose randomly a service instance
    IRule rule = new RandomRule();
    
    return rule;
  }
  
}
