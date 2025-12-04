package com.example.authorization_server.config;

import com.example.authorization_server.repository.RegisteredClientJpaRepository;
import com.example.authorization_server.domain.RegisteredClientEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RegisteredClientInitializer implements CommandLineRunner {

    private final RegisteredClientJpaRepository registeredClientRepository;

    @Override
    public void run(String... args) throws Exception {
        // Always recreate the default client to ensure correct configuration
        registeredClientRepository.deleteByClientId("pkce-client");

        RegisteredClientEntity client = new RegisteredClientEntity();
        client.setId(UUID.randomUUID().toString());
        client.setClientId("pkce-client");
        client.setClientName("PKCE Public Client");
        client.setClientSecret(null);
        client.setClientAuthenticationMethods("none");
        client.setAuthorizationGrantTypes("authorization_code,refresh_token");
        client.setRedirectUris("http://127.0.0.1:8080/callback");
        client.setScopes("read,write");
        // Token settings: 5 min access token, 7 days refresh token
        client.setTokenSettings("{\"@class\":\"java.util.LinkedHashMap\",\"access.token.time-to-live\":[\"java.time.Duration\",\"PT5M\"],\"refresh.token.time-to-live\":[\"java.time.Duration\",\"P7D\"],\"reuse.refresh.tokens\":true}");
        client.setClientSettings("{\"@class\":\"java.util.LinkedHashMap\"}");

        registeredClientRepository.save(client);
        System.out.println("✓ Default PKCE client registered successfully with refresh token support!");
    }
}
