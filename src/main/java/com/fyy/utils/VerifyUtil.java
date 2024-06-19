package com.fyy.utils;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.IoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 *
 * @date 2024-06-11 18:34:51
 * @description
 */
@Slf4j
@Component
public class VerifyUtil {
    @Autowired
    RedisTemplate<Object, Object> redisTemplate;
    @Value("${verify.width}")
    private Integer width;
    @Value("${verify.height}")
    private Integer height;
    @Value("${verify.codeCount}")
    private Integer codeCount;
    @Value("${verify.lineCount}")
    private Integer lineCount;


    public  String getVerify() {
        log.info("用户  获取验证码 {}", LocalDateTime.now());
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(width, height, codeCount, lineCount);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        lineCaptcha.write(outputStream);
        byte[] bytes = outputStream.toByteArray();
        IoUtil.close(outputStream);
        redisTemplate.opsForValue().set("verify", lineCaptcha.getCode(), 1, TimeUnit.MINUTES);
        return Base64.encode(bytes);
    }
}
