package com.example.cyclean_jwt.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.cyclean_jwt.config.auth.PrincipalDetails;
import com.example.cyclean_jwt.model.User;
import com.example.cyclean_jwt.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.cyclean_jwt.config.jwt.JwtProperties.*;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("회원가입이 되었습니다.");
        log.info("인증이나 권한이 필요한 주소 요청이 됨");
        String jwtHeader = request.getHeader(HEADER_STRING);

        // header가 있는지 확인
        if (jwtHeader == null || !jwtHeader.startsWith(TOKEN_PREFIX)){
            chain.doFilter(request, response);
            return;
        }

        String jwtToken = request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX, "");

        String username = JWT.require(Algorithm.HMAC512(SECRET)).build().verify(jwtToken)
                .getClaim("username").asString();

        if (username != null){
            log.info("username 정상");
            log.info("user : " + username);
            User user = userRepository.findByUsername(username);

            PrincipalDetails principalDetails = new PrincipalDetails(user);
            log.info("principalDetails : " + principalDetails.getUsername() + "가 잘들어왔습니다.");

            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);
        }
    }
}
