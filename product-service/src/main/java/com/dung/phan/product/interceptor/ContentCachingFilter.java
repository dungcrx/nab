package com.dung.phan.product.interceptor;


import com.dung.phan.product.interceptor.dto.HttpExchange;
import com.dung.phan.product.message.AuditProduceMessage;
import com.dung.phan.product.utils.GsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Component
@WebFilter(filterName = "ContentCachingFilter", urlPatterns = "/*")
@Slf4j
public class ContentCachingFilter extends OncePerRequestFilter {
        @Autowired
    private AuditProduceMessage auditProduceMessage;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("IN  ContentCachingFilter ");
        CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(httpServletRequest);
        CompletableFuture.runAsync(()-> doAudit(httpServletRequest)).exceptionally(ex-> {
            log.error("Error during push message {}",ex);
            return null;
        });
        filterChain.doFilter(cachedBodyHttpServletRequest, httpServletResponse);
    }

    private void doAudit(HttpServletRequest request){
        try{
            CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(request);
            HttpExchange dto = HttpExchange.builder()
                    .url(request.getRequestURI())
                    .method(request.getMethod())
                    .dateTimeStamp(new Date().getTime())
                    .body(getRequestData(cachedBodyHttpServletRequest))
                    .build();
           auditProduceMessage.sendKafkaMessage(GsonUtil.singletonGson().toJson(dto));
        } catch (Exception ex) {
            log.error(" could not audit with error {}",ex.getMessage());
        }

    }

    private static String getRequestData(final CachedBodyHttpServletRequest requestWrapper) throws IOException {
        InputStream inputStream = requestWrapper.getInputStream();
        InputStreamReader isReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(isReader);
        StringBuffer sb = new StringBuffer();
        String str;
        while((str = reader.readLine())!= null){
            sb.append(str);
        }
        return sb.toString();
    }
}
