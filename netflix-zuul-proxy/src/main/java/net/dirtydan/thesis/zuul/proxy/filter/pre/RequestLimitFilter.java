package net.dirtydan.thesis.zuul.proxy.filter.pre;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;


//TODO: use a leaky-bucket algorithm


@Component
public class RequestLimitFilter extends ZuulFilter {
  
  static final int REQUEST_TRESHOLD = 20;
  
  Map<String, Integer> requestCounter = new HashMap<>();
  
  
  
  @Override
  public Object run() {
    final RequestContext ctx = RequestContext.getCurrentContext();
    HttpServletRequest request = ctx.getRequest();
    String remoteAddr = request.getRemoteAddr();
    
    if (requestCounter.containsKey(remoteAddr)) {
      int numRequest = requestCounter.get(remoteAddr) + 1;
      
      if (numRequest > REQUEST_TRESHOLD) {
        rejectRequest("request treshold reached "
                      + "(" + REQUEST_TRESHOLD + ")", 429);
      }
      requestCounter.put(remoteAddr, numRequest);
    } else {
      requestCounter.put(remoteAddr, 0);
    }
    
    return null;
  }
  
  @Override
  public boolean shouldFilter() { return true; }
  
  @Override
  public String filterType() { return "pre"; }
  
  @Override
  public int filterOrder() { return 20; }
  
  
  
  private void rejectRequest(String message, int code) {
    final RequestContext ctx = RequestContext.getCurrentContext();
    
    ctx.setResponseStatusCode(code);
    ctx.setResponseBody(message);
    ctx.setSendZuulResponse(false);
  }
  
}