package com.pharmaflow.demo.Services.Impl;

import com.pharmaflow.demo.Dto.UserDto;
import com.pharmaflow.demo.Entities.User;
import com.pharmaflow.demo.Exceptions.ResourceNotFoundException;
import com.pharmaflow.demo.Mappers.UserMapper;
import com.pharmaflow.demo.Repositories.UserRepository;
import com.pharmaflow.demo.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto getUserById(UUID userId) {
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User Not Found")
        );
        return this.userMapper.toDto(user);
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
