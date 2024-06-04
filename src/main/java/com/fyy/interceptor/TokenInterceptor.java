package com.fyy.interceptor;

import com.fyy.common.MyException;
import com.fyy.common.StatusCodeEnum;
import com.fyy.context.BaseContext;
import com.fyy.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

/**
 *
 * @date 2024-05-17 21:44:39
 * @description
 */
@Component
@SuppressWarnings("all")
public class TokenInterceptor implements HandlerInterceptor {
    @Value("${jwt.key}")
    private String secretKey;

    @Value("${jwt.name}")
    private String tokenName;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            String token = request.getHeader(tokenName);
            // 解析Token，获取其中的Claims对象
            Claims claims = JwtUtil.parseToken(secretKey, token);
            // 从Claims中获取用户ID，并设置到上下文中
            Object t = claims.get("teacherId");
            Object s = claims.get("studentId");
            if(t!=null) BaseContext.setCurrentId(Long.valueOf(Objects.requireNonNull(t.toString())));
            else BaseContext.setCurrentId( Long.valueOf(Objects.requireNonNull(claims.get("studentId")).toString()));
            return true;
        } catch (Exception ex) {
            // 解析Token失败，抛出自定义业务异常
            throw new MyException(StatusCodeEnum.NOT_LOGIN);
        }

    }

}
