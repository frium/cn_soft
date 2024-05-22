package com.fyy.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @date 2024-05-21 16:29:51
 * @description
 */

@Component
@SuppressWarnings("all")
public class RoleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String role = (String) request.getSession().getAttribute("userRole");
        String uri = request.getRequestURI();
        System.out.println(uri);
        System.out.println(role);
        // 根据角色和请求的URI判断是否有访问权限
        if ("student".equals(role) && uri.startsWith("/teacher")) {
           //重定向一下
            return false;
        } else if ("teacher".equals(role) && uri.startsWith("/student")) {
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 可以在这里添加一些处理逻辑
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 可以在这里添加一些处理逻辑
    }
}
