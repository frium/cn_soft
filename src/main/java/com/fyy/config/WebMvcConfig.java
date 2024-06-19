package com.fyy.config;

import com.fyy.interceptor.IpInterceptor;
import com.fyy.interceptor.RoleInterceptor;
import com.fyy.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @date 2024-05-20 21:05:20
 * @description
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private TokenInterceptor tokenInterceptor;
    @Autowired
    private RoleInterceptor roleInterceptor;
    @Autowired
    private IpInterceptor ipInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(ipInterceptor)
                .addPathPatterns("/**")//添加拦截路径
                .order(Ordered.HIGHEST_PRECEDENCE);
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")//添加拦截路径
                .excludePathPatterns("/error", "/*/login", "/*/register", "/*/getVerify", "/*/forgetPassword")
                .order(100);
        registry.addInterceptor(roleInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/*/login", "/*/register", "/*/getVerify", "/*/forgetPassword")
                .order(10);
    }
}
