package com.fyy.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 *
 * @date 2024-05-21 16:29:51
 * @description
 */

@Slf4j
@Component
@SuppressWarnings("all")
public class RoleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String role = (String) request.getSession().getAttribute("userRole");
        String uri = request.getRequestURI();
        log.info(role+uri);
        // 根据角色和请求的URI判断是否有访问权限
        if ("student".equals(role) && uri.startsWith("/teacher")) {
            return false;
        } else if ("teacher".equals(role) && uri.startsWith("/student")) {
            return false;
        }
        return true;
    }
}
