package com.fyy.config;

import com.fyy.pojo.entity.SparkClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @date 2024-05-29 15:33:33
 * @description
 */
@Configuration
@Data
public class XingHuoConfig {
    @Value("${ai.id}")
    private String appid;
    @Value("${ai.secret}")
    private String apiSecret;
    @Value("${ai.key}")
    private String apiKey;

    @Bean
    public SparkClient sparkClient() {
        SparkClient sparkClient = new SparkClient();
        sparkClient.apiKey = apiKey;
        sparkClient.apiSecret = apiSecret;
        sparkClient.appid = appid;
        return sparkClient;
    }

}
