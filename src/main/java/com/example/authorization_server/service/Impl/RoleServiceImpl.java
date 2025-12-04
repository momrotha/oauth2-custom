package com.example.authorization_server.service.Impl;

import com.example.authorization_server.domain.Role;
import com.example.authorization_server.dto.role.CreateRole;
import com.example.authorization_server.repository.RoleRepository;
import com.example.authorization_server.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public void createRole(CreateRole createRole) {
        if(roleRepository.existsByName(createRole.name())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Role already exists");
        }

        Role role = new Role();
        role.setName(createRole.name());
        // Let JPA generate the ID
        roleRepository.save(role);
    }
}