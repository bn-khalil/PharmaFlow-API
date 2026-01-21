package com.pharmaflow.demo.Services.Impl;

import com.pharmaflow.demo.Dto.UserDto;
import com.pharmaflow.demo.Dto.UserRegister;
import com.pharmaflow.demo.Entities.User;
import com.pharmaflow.demo.Exceptions.ResourceNotFoundException;
import com.pharmaflow.demo.Mappers.UserMapper;
import com.pharmaflow.demo.Repositories.UserRepository;
import com.pharmaflow.demo.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers(String search) {

        if (search == null || search.trim().isEmpty()) {
            return userMapper.toDto(userRepository.findAll());
        }

        String pattern = "%" + search.toLowerCase() + "%";

        Specification<User> spec = (root, query, criteriaBuilder)->{
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), pattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), pattern)
            );
        };

        List<User> users = this.userRepository.findAll(spec);
        return this.userMapper.toDto(users);
    }

    @Override
    public UserDto getUserById(UUID userId) {
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User Not Found")
        );
        return this.userMapper.toDto(user);
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
    public void editUser(UserRegister userRegister) {

    }

    @Override
    public void deleteUser(UserDto userDto) {

    }
}
