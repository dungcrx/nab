package com.dung.phan.product.interceptor;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Component
@WebFilter(filterName = "ContentCachingFilter", urlPatterns = "/*")
public class CachedHttpServletRequestFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!isPost(request) && !isPut(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest(request);

        filterChain.doFilter(cachedRequest, response);
    }

    private boolean isPost(HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase(HttpMethod.POST.toString());
    }

    private boolean isPut(HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase(HttpMethod.PUT.toString());
    }
}