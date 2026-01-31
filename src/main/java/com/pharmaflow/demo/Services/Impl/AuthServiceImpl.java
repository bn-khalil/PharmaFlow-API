package com.pharmaflow.demo.Services.Impl;

import com.pharmaflow.demo.Dto.AuthResponse;
import com.pharmaflow.demo.Dto.UserLogin;
import com.pharmaflow.demo.Dto.UserRegister;
import com.pharmaflow.demo.Entities.User;
import com.pharmaflow.demo.Events.MailSendWelcomeEvet;
import com.pharmaflow.demo.Exceptions.ResourceDuplicated;
import com.pharmaflow.demo.Mappers.UserMapper;
import com.pharmaflow.demo.Repositories.UserRepository;
import com.pharmaflow.demo.Security.UserSecurity;
import com.pharmaflow.demo.Services.AuthService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.Elements;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.lang.model.element.Element;
import java.time.Duration;
import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor

public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    @Transactional
    public AuthResponse register(UserRegister userRegister) {
        if (this.userRepository.findUserByEmail(userRegister.email()).isPresent())
            throw new ResourceDuplicated("email already exist!");
        User user = this.userMapper.toEntity(userRegister);
        user.setPassword(this.encoder.encode(user.getPassword()));
        User savedUser = this.userRepository.save(user);
        applicationEventPublisher.publishEvent(new MailSendWelcomeEvet(
                savedUser.getEmail(),
                savedUser.getFirstName(),
                savedUser.getRole().toString())
        );
        return AuthResponse.builder()
                .message(user.getFirstName() + " registered successfully!")
                .email(user.getEmail())
                .build();
    }

    @Override
    @Transactional
    @CacheEvict(value = "user_auth", key = "#userLogin.email")
    public AuthResponse login(UserLogin userLogin) {
        Authentication auth = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLogin.email(), userLogin.password())
        );

        UserSecurity userSecurity = (UserSecurity) auth.getPrincipal();
        String jwtToken = this.jwtService.generateToken(userSecurity);

        if (userSecurity == null)
            throw new RuntimeException("some thing wrong with Context is empty");

        return AuthResponse.builder()
                .message("singed successfully!")
                .email(userSecurity.getUsername())
                .token(jwtToken)
                .build();
    }

    @Override
    public void logout() {
        UserSecurity userSecurity = (UserSecurity) Objects.requireNonNull(SecurityContextHolder.getContext()
                .getAuthentication()).getPrincipal();
        String token = userSecurity.getToken();
        User user  = userSecurity.getUser();
        Date timeout = jwtService.extractExpiration(token);
        if (timeout.getTime() > System.currentTimeMillis())
            redisTemplate.opsForValue().set("black_token::" + token,
                    token,
                    Duration.ofMillis(timeout.getTime() - System.currentTimeMillis()));
    }
}
