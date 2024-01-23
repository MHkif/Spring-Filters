package com.yc.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@Order(1) //Specifies the priority of this filter in the chain of filters. A lower order value means it's executed earlier.
@Slf4j
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {


        final String authorization = request.getHeader("authorization");
        Collections.list(request.getHeaderNames())
                .forEach(header -> {
                    log.info("Header: {} = {}", header, request.getHeader(header));
                });

        log.info("Authorization : {}", authorization);

        //In case of security checks , For example : Authorization
        if (authorization != null) {
            // Continue with the filter chain
            chain.doFilter(request, response);
        } else {
            response.sendRedirect("/unauthorized"); // Redirect to unauthorized page
        }
    }

}