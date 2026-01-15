package com.pharmaflow.demo.Services.Impl;

import com.pharmaflow.demo.Dto.AuthResponse;
import com.pharmaflow.demo.Dto.UserLogin;
import com.pharmaflow.demo.Dto.UserRegister;
import com.pharmaflow.demo.Entities.User;
import com.pharmaflow.demo.Exceptions.ResourceDuplicated;
import com.pharmaflow.demo.Exceptions.ResourceNotFoundException;
import com.pharmaflow.demo.Mappers.UserMapper;
import com.pharmaflow.demo.Repositories.UserRepository;
import com.pharmaflow.demo.Services.AuthService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.encoder = new BCryptPasswordEncoder(12);
    }

    @Override
    @Transactional
    public AuthResponse register(UserRegister userRegister) {
        if (this.userRepository.findUserByEmail(userRegister.email()).isPresent())
            throw new ResourceDuplicated("email already exist!");
        User user = this.userMapper.toEntity(userRegister);
        user.setPassword(this.encoder.encode(user.getPassword()));
        this.userRepository.save(user);
        return AuthResponse.builder()
                .message(user.getFirstName() + " registered successfully!")
                .email(user.getEmail())
                .build();
    }

    @Override
    @Transactional
    public AuthResponse login(UserLogin userLogin) {
        User user = this.userRepository.findUserByEmail(userLogin.email()).orElseThrow(
                () -> new ResourceNotFoundException("no subscription with this email")
        );

        return AuthResponse.builder()
                .message(user.getFirstName() + "singed successfully!")
                .email(user.getEmail())
                .build();
    }
}
