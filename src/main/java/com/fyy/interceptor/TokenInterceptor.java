package com.fyy.interceptor;

import com.fyy.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 *
 * @date 2024-05-17 21:44:39
 * @description
 */
public class TokenInterceptor implements HandlerInterceptor {
    //在请求处理方法前被调用
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token =request.getHeader("token"); //将token放到请求头中
        if(!JwtUtil.checkToken(token)){
            return false;
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
