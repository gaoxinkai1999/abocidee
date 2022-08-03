package com.abocidee.Filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * url与cookie拦截器
 */
@Component
@WebFilter(urlPatterns = "/*")
@Order(1)
@Slf4j
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("我是自定义过滤器");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
//      获取请求路径
        String requestURI = httpServletRequest.getRequestURI();
        //如果路径为注册，则放行，否则验证cookie
        log.info(requestURI);
        if (requestURI.equals("/user/login") || requestURI.equals("/qrcode/getimg") || requestURI.equals("/qrcode/checkstate")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            Cookie[] cookies = httpServletRequest.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username") && !cookie.getValue().isEmpty()) {
                    filterChain.doFilter(servletRequest, servletResponse);
                }
            }
        }

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
