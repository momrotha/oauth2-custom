package com.example.authorization_server.domain;

import com.example.authorization_server.dto.ClientType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.time.Instant;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
public class Client {
    @Id
    private String id;

    private String clientId;
    private String clientSecret;
    private Instant clientIdIssuedAt;
    private Instant clientSecretExpiresAt;
    private String clientName;

    @Enumerated(EnumType.STRING)
    private ClientType clientType;

    @Column(length = 1000)
    private String clientAuthenticationMethods; // store JSON
    @Column(length = 1000)
    private String authorizationGrantTypes;     // store JSON
    @Column(length = 1000)
    private String redirectUris;                // store JSON
    @Column(length = 1000)
    private String postLogoutRedirectUris;      // store JSON
    @Column(length = 1000)
    private String scopes;                      // store JSON
    @Column(length = 2000)
    private String clientSettings;              // store JSON
    @Column(length = 2000)
    private String tokenSettings;               // store JSON
}
