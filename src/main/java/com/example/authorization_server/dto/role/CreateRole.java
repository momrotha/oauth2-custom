package com.example.authorization_server.dto.role;

import lombok.Builder;

@Builder
public record CreateRole (

        String name
){
}
