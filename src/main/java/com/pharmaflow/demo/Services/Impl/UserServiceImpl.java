package com.pharmaflow.demo.Services.Impl;

import com.pharmaflow.demo.Dto.UserDto;
import com.pharmaflow.demo.Entities.User;
import com.pharmaflow.demo.Mappers.UserMapper;
import com.pharmaflow.demo.Repositories.UserRepository;
import com.pharmaflow.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepository.findAll();
        return this.userMapper.toDto(users);
    }

    @Override
    public List<UserDto> getPharmacists() {
        return List.of();
    }

    @Override
    public List<UserDto> getAdmins() {
        return List.of();
    }

    @Override
    public void addUser(UserDto userDto) {

    }

    @Override
    public void deleteUser(UserDto userDto) {

    }
}
