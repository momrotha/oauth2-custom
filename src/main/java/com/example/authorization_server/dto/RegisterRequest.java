package com.example.authorization_server.dto;


public record RegisterRequest(
        String username,
        String password,
        String role
) {
}
