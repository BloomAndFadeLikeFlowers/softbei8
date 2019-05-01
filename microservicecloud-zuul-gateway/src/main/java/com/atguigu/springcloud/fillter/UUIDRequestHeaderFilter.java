package com.atguigu.springcloud.fillter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Component
public class UUIDRequestHeaderFilter extends ZuulFilter {
    private Logger logger = LoggerFactory.getLogger("requestUUIDLogger");

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        String requestUUID = UUID.randomUUID().toString();
        ctx.addZuulRequestHeader("requestUUID", requestUUID);
        logger.info("gateway-create-requestUUID:" + requestUUID);

        return null;
    }
}
