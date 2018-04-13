package com.doddzhang.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: capricornus
 * @description: zuul filter
 * @author: zhangdong
 * @create: 2018-04-13 11:28
 **/
public class AccessFilter extends ZuulFilter{

    private static Logger log = LoggerFactory.getLogger(AccessFilter.class);

    @Override
    public String filterType() {
        //过滤器的类型，它决定过滤器在请求的哪个生命周期中执行。这里定义为pre，代表会在请求被路由之前执行。
        return "pre";
    }

    @Override
    public int filterOrder() {
        //过滤器的执行顺序。当请求在一个阶段中存在多个过滤器时，需要根据该方法返回的值来依次执行。
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        //判断该过滤器是否需要被执行。这里我们直接返回了true，因此该过滤器对所有请求都会生效。实际运用中我们可以利用该函数来指定过滤器的有效范围。
        return true;
    }

    @Override
    public Object run() {
        //过滤器的具体逻辑。这里我们通过ctx.setSendZuulResponse(false)令zuul过滤该请求，不对其进行路由，
        //然后通过ctx.setResponseStatusCode(401)设置了其返回的错误码，当然我们也可以进一步优化我们的返回，比如，通过ctx.setResponseBody(body)对返回body内容进行编辑等。
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest httpServletRequest = requestContext.getRequest();

        log.info("send {} request to {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURL().toString());

        Object accessToken = httpServletRequest.getParameter("accessToken");
        if(accessToken == null) {
            log.warn("access token is empty");
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(401);
            return null;
        }
        log.info("access token ok");
        return null;
    }
}
