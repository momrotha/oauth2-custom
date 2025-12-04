package com.example.authorization_server.dto.role;

import lombok.Builder;

@Builder
public record ResponseRole(
        String id,

        String role_name
) {
}
