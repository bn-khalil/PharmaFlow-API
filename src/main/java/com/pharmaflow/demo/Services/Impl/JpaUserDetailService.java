package com.pharmaflow.demo.Services.Impl;

import com.pharmaflow.demo.Entities.User;
import com.pharmaflow.demo.Exceptions.ResourceNotFoundException;
import com.pharmaflow.demo.Repositories.UserRepository;
import com.pharmaflow.demo.Security.UserSecurity;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class JpaUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Cacheable(value = "user_auth", key = "#username")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("yes");
        User user = this.userRepository.findUserByEmail(username).orElseThrow(
                () -> new ResourceNotFoundException("User Not Found!")
        );
        return new UserSecurity(user, Map.of());
    }
}
