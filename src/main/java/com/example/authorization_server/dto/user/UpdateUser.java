package com.example.authorization_server.dto.user;


import lombok.Builder;

@Builder
public record UpdateUser(

        String username
) {
}
