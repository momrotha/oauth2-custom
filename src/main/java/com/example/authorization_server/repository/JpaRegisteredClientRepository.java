package com.example.authorization_server.repository;


import com.example.authorization_server.domain.RegisteredClientEntity;

import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class JpaRegisteredClientRepository implements RegisteredClientRepository {

    private final RegisteredClientJpaRepository repo;

    public JpaRegisteredClientRepository(RegisteredClientJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public void save(RegisteredClient registeredClient) {
        RegisteredClientEntity entity = new RegisteredClientEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setClientId(registeredClient.getClientId());
        entity.setClientSecret(registeredClient.getClientSecret());
        entity.setClientName(registeredClient.getClientName());
        entity.setClientAuthenticationMethods(String.join(",",
                registeredClient.getClientAuthenticationMethods()
                        .stream()
                        .map(ClientAuthenticationMethod::getValue)
                        .collect(Collectors.toSet())
        ));
        entity.setAuthorizationGrantTypes(String.join(",",
                registeredClient.getAuthorizationGrantTypes()
                        .stream()
                        .map(AuthorizationGrantType::getValue)
                        .collect(Collectors.toSet())
        ));
        entity.setRedirectUris(String.join(",", registeredClient.getRedirectUris()));
        entity.setScopes(String.join(",", registeredClient.getScopes()));
        // tokenSettings and clientSettings can be serialized as JSON if needed
        repo.save(entity);
    }

    @Override
    public RegisteredClient findById(String id) {
        Optional<RegisteredClientEntity> opt = repo.findById(id);
        return opt.map(this::toRegisteredClient).orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Optional<RegisteredClientEntity> opt = repo.findByClientId(clientId);
        return opt.map(this::toRegisteredClient).orElse(null);
    }

    private RegisteredClient toRegisteredClient(RegisteredClientEntity entity) {
        RegisteredClient.Builder builder = RegisteredClient.withId(entity.getId())
                .clientId(entity.getClientId())
                .clientSecret(entity.getClientSecret())
                .clientName(entity.getClientName());

        // Parse client authentication methods (comma-separated)
        if (entity.getClientAuthenticationMethods() != null && !entity.getClientAuthenticationMethods().isEmpty()) {
            Arrays.stream(entity.getClientAuthenticationMethods().split(","))
                    .forEach(method -> builder.clientAuthenticationMethod(new ClientAuthenticationMethod(method.trim())));
        }

        // Parse authorization grant types (comma-separated)
        if (entity.getAuthorizationGrantTypes() != null && !entity.getAuthorizationGrantTypes().isEmpty()) {
            Arrays.stream(entity.getAuthorizationGrantTypes().split(","))
                    .forEach(type -> builder.authorizationGrantType(new AuthorizationGrantType(type.trim())));
        }

        // Parse redirect URIs (comma-separated)
        if (entity.getRedirectUris() != null && !entity.getRedirectUris().isEmpty()) {
            Arrays.stream(entity.getRedirectUris().split(","))
                    .forEach(uri -> builder.redirectUri(uri.trim()));
        }

        // Parse scopes (comma-separated)
        if (entity.getScopes() != null && !entity.getScopes().isEmpty()) {
            Arrays.stream(entity.getScopes().split(","))
                    .forEach(scope -> builder.scope(scope.trim()));
        }

        return builder.build();
    }
}
