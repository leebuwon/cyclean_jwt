package com.example.cyclean_jwt.config;

import com.example.cyclean_jwt.config.jwt.JwtAuthenticationFilter;
import com.example.cyclean_jwt.config.jwt.JwtAuthorizationFilter;
import com.example.cyclean_jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

// 시큐리티 선언!
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsFilter corsFilter;

    private final UserRepository userRepository;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.addFilterBefore(new MyFilter3(), BasicAuthenticationFilter.class); // SecurityContextPersistenceFilter 사용하셨는데 deprecated 됨
        return http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 사용하지 않는다.
                .and()
                .formLogin().disable()
                .httpBasic().disable() // 기본인증 방식이 아닌 bearer 방식을 사용할  것이다.
                .apply(new MyCustomDsl()) // 커스텀 필터 등록
                .and()
                .authorizeRequests(authroize -> authroize
                        .antMatchers("/","/user/**")
                        .access("hasRole('ROLE_USER')")
                        .anyRequest().permitAll())
                .build();
//"account/login","account/register"
    }

    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http
                    .addFilter(corsFilter) // @CrossOrigin (인증 x), 시큐리티 필어테 등록 인증
                    .addFilter(new JwtAuthenticationFilter(authenticationManager)) //  authenticationManager 이놈을 던져줘야 함 (login을 처리하는 필터라서)
                    .addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository));
        }
    }
}
