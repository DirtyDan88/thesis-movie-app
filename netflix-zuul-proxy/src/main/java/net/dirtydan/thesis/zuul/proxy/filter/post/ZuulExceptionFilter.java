package net.dirtydan.thesis.zuul.proxy.filter.post;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;



@Component
public class ZuulExceptionFilter extends ZuulFilter {
  
  @Override
  public boolean shouldFilter() {
    // execute the filter only if a error-code is set
    return RequestContext.getCurrentContext().containsKey("error.status_code");
  }
  @Override
  public int filterOrder() {
    // the filter needs to run before "SendErrorFilter", a pre-defined post
    // filter which also check for the "error.status_code" key
    return -1;
  }
  @Override
  public String filterType() { return "post"; }
  
  @Override
  public Object run() {
    final RequestContext ctx = RequestContext.getCurrentContext();
    
    try {
      Object ex = ctx.get("error.exception");
      
      if (ex != null && ex instanceof ZuulException) {
//        ZuulException zuulException = (ZuulException)ex;
//        System.out.println("### Zuul failure detected: " + 
//                                zuulException.getMessage());
        
        // remove error status code to prevent further error handling
        ctx.remove("error.status_code");
        ctx.setResponseStatusCode(500); // set a HTTP status code
        ctx.setResponseBody("a meaningful message for the user");
        ctx.setSendZuulResponse(false);
      }
    } catch (Exception e) {
      //LOG.error("Exception filtering in custom error filter", e);
      //ReflectionUtils.rethrowRuntimeException(ex);
    }
    
    return null;
  }
}
