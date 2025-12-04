package com.example.authorization_server.dto;

import lombok.Data;

@Data
public class ClientCredentialsRequest extends CreateClient {
    private Integer accessTokenTTLHours = 8;
    private Boolean reuseRefreshTokens = true;
}