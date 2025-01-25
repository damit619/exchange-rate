package com.exchangerate.config.feign;

import feign.Logger;
import feign.codec.ErrorDecoder;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignOpenExchangeErrorDecoder();
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }
}
