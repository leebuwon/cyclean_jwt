package com.example.cyclean_jwt.config.auth;

import com.example.cyclean_jwt.model.User;
import com.example.cyclean_jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("PrincipalDetailsService의 loadUserByUsername()함수가 실행됨");
        User user = userRepository.findByUsername(username);
        log.info("user = {}" + user);

        return new PrincipalDetails(user);
    }
}
