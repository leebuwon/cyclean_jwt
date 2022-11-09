package com.example.cyclean_jwt.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(length = 200, unique = true)
    private String username;

    @Column(length = 200)
    private String password;

    @Column(length = 200)
    private String role;
}
