package com.pharmaflow.demo.Services;

import com.pharmaflow.demo.Dto.EditUser;
import com.pharmaflow.demo.Dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDto getUserById(UUID userId);
    List<UserDto> getAllUsers(String search, String role);
    UserDto editUser(EditUser editUser);
    void changeUserStatus (UUID userId);
}
