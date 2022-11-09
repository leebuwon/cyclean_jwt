package com.example.cyclean_jwt.config.jwt;

// 인터페이스로 만들어서 작성 오류가 발생하는 것을 막는다.

public interface JwtProperties {
    String SECRET = "cyclean"; // 우리 서버만 알고 있는 비밀값
    int EXPIRATION_TIME = 60000 * 240; // 토큰 지속 시간
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}