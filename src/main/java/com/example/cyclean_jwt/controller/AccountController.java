package com.example.cyclean_jwt.controller;

import com.example.cyclean_jwt.model.User;
import com.example.cyclean_jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
@Slf4j
public class AccountController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // login 조회
    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("account/login");
        return modelAndView;
    }

    // register 조회
    @GetMapping("/register")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView("account/register");
        return modelAndView;
    }

    // register 값 넣기
    @PostMapping("/register")
    public String register(@RequestBody User user){
        String rawPassword = user.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setRole("ROLE_USER");
        userRepository.save(user);
        return "회원 가입 완료";
    }

    // user 권한을 가진 사용자가 들어갈 수 있은 시범용
    @GetMapping("api/user")
    @ResponseBody
    public String user(){
        // user 확인용
        return "user 권한이 있습니다.";
    }

    // 정보가 잘 저장되었는지 확인하는 시범용
    @GetMapping("/users")
    public List<User> users() {
        return userRepository.findAll();
    }
}
