package com.example.authorization_server.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "oauth2_registered_client")
@Getter
@Setter
public class RegisteredClientEntity {

    @Id
    private String id;

    @Column(unique = true)
    private String clientId;

    private String clientIdIssuedAt;

    private String clientSecret;
    private String clientSecretExpiresAt;

    private String clientName;

    @Column(columnDefinition = "TEXT")
    private String clientAuthenticationMethods;

    @Column(columnDefinition = "TEXT")
    private String authorizationGrantTypes;

    @Column(columnDefinition = "TEXT")
    private String redirectUris;

    @Column(columnDefinition = "TEXT")
    private String scopes;

    // token settings
    @Column(columnDefinition = "TEXT")
    private String tokenSettings;

    // client settings
    @Column(columnDefinition = "TEXT")
    private String clientSettings;
}

