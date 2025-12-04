package com.example.authorization_server.service;


import com.example.authorization_server.dto.user.CreateUser;
import com.example.authorization_server.dto.user.UpdateUser;
import com.example.authorization_server.dto.user.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> getAll();

    UserResponse getById(String id);

    void createUser(CreateUser create);

    void updateUserById(UpdateUser updateUser, String id);

    void deleteUserById(String id);
}
