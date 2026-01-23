package com.pharmaflow.demo.Services.Impl;

import com.pharmaflow.demo.Dto.AuthResponse;
import com.pharmaflow.demo.Dto.UserLogin;
import com.pharmaflow.demo.Dto.UserRegister;
import com.pharmaflow.demo.Entities.User;
import com.pharmaflow.demo.Exceptions.BadRequestException;
import com.pharmaflow.demo.Exceptions.ResourceDuplicated;
import com.pharmaflow.demo.Exceptions.ResourceNotFoundException;
import com.pharmaflow.demo.Mappers.UserMapper;
import com.pharmaflow.demo.Repositories.UserRepository;
import com.pharmaflow.demo.Security.UserSecurity;
import com.pharmaflow.demo.Services.AuthService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

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
        Authentication auth = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLogin.email(), userLogin.password())
        );

        UserSecurity userSecurity = (UserSecurity) auth.getPrincipal();
        String jwtToken = this.jwtService.generateToken(userSecurity);

        return AuthResponse.builder()
                .message("singed successfully!")
                .email(userSecurity.getUsername())
                .token(jwtToken)
                .build();
    }
}
