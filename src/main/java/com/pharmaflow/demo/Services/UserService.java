package com.pharmaflow.demo.Services;

import com.pharmaflow.demo.Dto.UserDto;
import com.pharmaflow.demo.Dto.UserRegister;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    UserDto getUserById(UUID userId);
    List<UserDto> getAllUsers(String search);
    List<UserDto> getPharmacists();
    List<UserDto> getAdmins();
    void editUser(UserRegister userRegister);
    void deleteUser(UserDto userDto);
}
