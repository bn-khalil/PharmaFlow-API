package com.pharmaflow.demo.Services;

import com.pharmaflow.demo.Dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    List<UserDto> getPharmacists();
    List<UserDto> getAdmins();
    void addUser(UserDto userDto);
    void deleteUser(UserDto userDto);
}
