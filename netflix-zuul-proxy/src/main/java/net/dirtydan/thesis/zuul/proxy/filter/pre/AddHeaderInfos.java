package net.dirtydan.thesis.zuul.proxy.filter.pre;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;



@Component
public class AddHeaderInfos extends ZuulFilter {
  
  @Override
  public Object run() {
    final RequestContext ctx = RequestContext.getCurrentContext();
    
    ctx.addZuulRequestHeader("zuul-pre-filtered", "true");
    ctx.addZuulRequestHeader("zuul-header-info", "This request was pre-filtered by Zuul!");
    
    return null;
  }
  
  @Override
  public boolean shouldFilter() { return true; }
  
  @Override
  public int filterOrder() { return 0; }
  
  @Override
  public String filterType() { return "pre"; }
  
}