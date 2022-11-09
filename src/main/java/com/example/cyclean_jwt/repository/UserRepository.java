package com.example.cyclean_jwt.repository;

import com.example.cyclean_jwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// CRUD 기능을 JpaRepository가 가지고 있다.
// @Reopsitory라는 어노테이션이 없어도 IoC된다. 이유는 JpaRepository에 등록되어있기 때문.
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsername(String username);
}
