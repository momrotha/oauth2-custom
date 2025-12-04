package com.example.authorization_server.service;

import com.example.authorization_server.dto.RegisterRequest ;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {
    String register(RegisterRequest req);
    UserDetails loadUserByUsername(String username);

}

