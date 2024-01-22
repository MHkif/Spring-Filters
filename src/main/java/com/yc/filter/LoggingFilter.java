package com.yc.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;

@Component
@Order(1)
//Specifies the priority of this filter in the chain of filters. A lower order value means it's executed earlier.
@Slf4j
public class LoggingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic if needed
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final HttpServletResponse httpRes = (HttpServletResponse) response;

        final String authorization = httpRequest.getHeader("authorization");
        Collections.list(httpRequest.getHeaderNames())
                .forEach(header -> {
                    log.info("Header: {} = {}", header, httpRequest.getHeader(header));
                });

        log.info("Authorization : {}", authorization);

        //In case of security checks , For example : Authorization
        if (authorization != null) {
            // Continue with the filter chain
            chain.doFilter(request, response);
        } else {
            httpRes.sendRedirect("/unauthorized"); // Redirect to unauthorized page
        }
    }


    @Override
    public void destroy() {
        // Cleanup logic if needed
        Filter.super.destroy();
    }
}