package com.longyg.zuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 后置过滤器，将关联ID注入响应Header，返回给调用者
 */
@Component
public class ResponseFilter extends ZuulFilter {
    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;
    private static final Logger logger = LoggerFactory.getLogger(ResponseFilter.class.getName());

    @Autowired
    private FilterUtils filterUtils;

    @Override
    public String filterType() {
        return FilterUtils.POST_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        logger.info("Adding Correlation ID to outbound headers: {}", filterUtils.getCorrelationId());

        ctx.getResponse().addHeader(FilterUtils.CORRECTION_ID, filterUtils.getCorrelationId());

        logger.info("Completing outgoing request for {}", ctx.getRequest().getRequestURI());
        return null;
    }
}
