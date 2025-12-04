package com.example.authorization_server.repository;

//import com.authorization.server.OAuth2.domain.OAuth2AuthorizationEntity;
import com.example.authorization_server.domain.OAuth2AuthorizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.authorization_server.domain.RegisteredClientEntity;
import java.util.Optional;

public interface OAuth2AuthorizationRepository extends JpaRepository<OAuth2AuthorizationEntity, String> {
    Optional<OAuth2AuthorizationEntity> findByAuthorizationCode(String code);
    Optional<OAuth2AuthorizationEntity> findByAccessToken(String accessToken);
    Optional<OAuth2AuthorizationEntity> findByRefreshToken(String refreshToken);
}
