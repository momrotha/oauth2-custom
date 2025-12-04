package com.example.authorization_server.repository;

import com.example.authorization_server.domain.Client;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DbRegisteredClientRepository implements RegisteredClientRepository {

    private final ClientRepository clientRepository;
    private final ObjectMapper objectMapper;

    public DbRegisteredClientRepository(ClientRepository clientRepository, ObjectMapper objectMapper) {
        this.clientRepository = clientRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void save(org.springframework.security.oauth2.server.authorization.client.RegisteredClient registeredClient) {
        // your save code here
    }

    @Override
    public org.springframework.security.oauth2.server.authorization.client.RegisteredClient findById(String id) {
        return clientRepository.findById(id)
                .map(this::toRegisteredClient)
                .orElse(null);
    }

    @Override
    public org.springframework.security.oauth2.server.authorization.client.RegisteredClient findByClientId(String clientId) {
        return clientRepository.findByClientId(clientId)
                .map(this::toRegisteredClient)
                .orElse(null);
    }

    // <-- Move your method here inside the class
    private org.springframework.security.oauth2.server.authorization.client.RegisteredClient toRegisteredClient(Client entity) {
        try {
            return org.springframework.security.oauth2.server.authorization.client.RegisteredClient.withId(entity.getId())
                    .clientId(entity.getClientId())
                    .clientSecret(entity.getClientSecret())
                    .clientName(entity.getClientName())
                    .clientAuthenticationMethods(authMethods -> {
                        try {
                            List<String> methods = objectMapper.readValue(entity.getClientAuthenticationMethods(), List.class);
                            methods.forEach(m -> authMethods.add(new ClientAuthenticationMethod(m)));
                        } catch (Exception e) {
                            throw new RuntimeException(e); // wrap checked exception
                        }
                    })
                    .authorizationGrantTypes(grants -> {
                        try {
                            List<String> grantTypes = objectMapper.readValue(entity.getAuthorizationGrantTypes(), List.class);
                            grantTypes.forEach(g -> grants.add(new AuthorizationGrantType(g)));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .redirectUris(uris -> {
                        try {
                            List<String> redirectUris = objectMapper.readValue(entity.getRedirectUris(), List.class);
                            redirectUris.forEach(uris::add);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .scopes(scopes -> {
                        try {
                            List<String> scopeList = objectMapper.readValue(entity.getScopes(), List.class);
                            scopeList.forEach(scopes::add);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .clientSettings(ClientSettings.builder().build())
                    .tokenSettings(TokenSettings.builder().build())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
