package com.fyy.utils;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.UUID;

/**
 *
 * @date 2024-05-17 21:21:52
 * @description
 */
public class JwtUtil {
    private static  long time = 100000*60*60;
    private static String sign ="admin";
    public static String createToken(){
        //创建一个JwtBuilder对象
        JwtBuilder jwtBuilder = Jwts.builder();
        return jwtBuilder.setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .claim("username", "tom")
                .setSubject("admin-test")
                .setExpiration(new Date(System.currentTimeMillis()+time))//过期时间
                .setId(UUID.randomUUID().toString())//id字段
                .signWith(SignatureAlgorithm.HS256,sign) //设置加密算法和签名
                .compact();
    }

    //封装验证token是否过期的方法
    public static boolean checkToken(String token){
        if(token==null|| token.isEmpty()){
            return  false;
        }
        try {
            Jws<Claims> claimsJws=Jwts.parser().setSigningKey(sign).parseClaimsJws(token);
        }catch (Exception e){
            e.printStackTrace();
            return  false;
        }
        return true;

    }

}
