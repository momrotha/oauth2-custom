package com.example.authorization_server.dto;

public record LoginRequest(
        String username,
        String password
) {
}
