package com.example.cyclean_jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        // 내 서버가 응답을 할 떄 Json을 자바스크립트에서 처리할 수 있게 할지를 설정하는것 false로 하면 javascript에서 응답을 하지 않는다.
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*"); // 모둔 ip에 응답
        config.addAllowedHeader("*"); // 모든 header에 응답
        config.addAllowedMethod("*"); // 모든 post, get, put, delete, patch 를 허용

        source.registerCorsConfiguration("/account/**", config);
        return new CorsFilter(source);
    }

}