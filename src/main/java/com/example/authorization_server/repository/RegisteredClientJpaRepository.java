package com.example.authorization_server.repository;

import com.example.authorization_server.domain.RegisteredClientEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegisteredClientJpaRepository
        extends JpaRepository<RegisteredClientEntity, String> {
    Optional<RegisteredClientEntity> findByClientId(String clientId);
    void deleteByClientId(String clientId);
}
