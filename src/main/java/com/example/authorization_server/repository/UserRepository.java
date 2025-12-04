package com.example.authorization_server.repository;


import com.example.authorization_server.domain.User;
import com.example.authorization_server.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}


//public interface UserRepository extends CrudRepository<UserEntity, String> { ... }


