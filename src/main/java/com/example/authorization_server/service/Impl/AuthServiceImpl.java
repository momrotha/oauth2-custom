package com.example.authorization_server.service.Impl;

import com.example.authorization_server.domain.Role;
import com.example.authorization_server.domain.UserEntity;
import com.example.authorization_server.dto.RegisterRequest;
import com.example.authorization_server.repository.UserRepository;
import com.example.authorization_server.service.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String register(RegisterRequest req) {
        if (userRepository.findByUsername(req.username()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        UserEntity user = new UserEntity();
        user.setUsername(req.username());
        user.setPassword(passwordEncoder.encode(req.password()));


        return "User registered successfully";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.authorization_server.domain.User userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Map UserEntity to UserDetails (Spring Security's User)
        return User.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .authorities(userEntity.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))  // Assuming Role has a 'name' field
                        .collect(Collectors.toList()))
                .build();
    }
}
