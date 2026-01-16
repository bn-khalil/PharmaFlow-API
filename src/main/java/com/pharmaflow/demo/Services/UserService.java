package com.pharmaflow.demo.Services;

import com.pharmaflow.demo.Dto.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    UserDto getUserById(UUID userId);
    List<UserDto> getAllUsers();
    List<UserDto> getPharmacists();
    List<UserDto> getAdmins();
    void addUser(UserDto userDto);
    void deleteUser(UserDto userDto);
}
