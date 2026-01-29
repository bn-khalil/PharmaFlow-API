package com.pharmaflow.demo.Services.Impl;

import com.pharmaflow.demo.Dto.EditUser;
import com.pharmaflow.demo.Dto.UserDto;
import com.pharmaflow.demo.Entities.User;
import com.pharmaflow.demo.Enums.Role;
import com.pharmaflow.demo.Exceptions.BadRequestException;
import com.pharmaflow.demo.Exceptions.ResourceNotFoundException;
import com.pharmaflow.demo.Mappers.UserMapper;
import com.pharmaflow.demo.Repositories.Specifications.UserSpecifications;
import com.pharmaflow.demo.Repositories.UserRepository;
import com.pharmaflow.demo.Security.UserSecurity;
import com.pharmaflow.demo.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CacheManager cacheManager;

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers(String search, String role) {

        Specification<User> spec = Specification.where(
                (root, query, criteriaBuilder)
                        -> criteriaBuilder.conjunction()
        );

        if (search != null && !search.trim().isEmpty())
            spec = spec.and(UserSpecifications.hasFirstName(search))
                    .or(UserSpecifications.hasFirstName(search));
        if (role != null && !role.trim().isEmpty())
            spec = spec.and(UserSpecifications.hasRole(role));
        spec = spec.and((root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.desc(root.get("role")));
                return null;
            }
        );
        List<User> users = this.userRepository.findAll(spec);
        return this.userMapper.toDto(users);
    }

    @Override
    @Cacheable(value = "user_dto", key = "#userId")
    public UserDto getUserById(UUID userId) {
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User Not Found")
        );
        return this.userMapper.toDto(user);
    }

    @Override
    @Transactional
    @CachePut(value = "user_dto", key = "#result.id")
    public UserDto editUser(EditUser editUser) {
        UserSecurity userService = (UserSecurity)SecurityContextHolder.
                getContext()
                .getAuthentication()
                .getPrincipal();

        User userTarget = userRepository.findById(userService.getUser().getId()).orElseThrow(
                () -> new ResourceNotFoundException("User not Found!")
        );

        // this trick here is for update only
        // the attributes that changed in EditUser dto
        userMapper.editDtoToEntity(editUser, userTarget);

        return this.userMapper.toDto(this.userRepository.save(userTarget));
    }

    @Override
    @Transactional
    public void changeUserStatus(UUID userId) {
        UserSecurity userService = (UserSecurity)SecurityContextHolder.
                getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userService.getUser();

        User userTarget = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not Found!")
        );

        if (user.getRole() != Role.ADMIN || userTarget.isFirstAdmin())
            throw new BadRequestException("Cannot disable this Account");

        // remove all caches related to user
        Objects.requireNonNull(cacheManager.getCache("user_auth")).evict(userTarget.getEmail());
        Objects.requireNonNull(cacheManager.getCache("user_dto")).evict(userId);

        userTarget.setActive(!userTarget.isActive());
        this.userRepository.save(userTarget);
    }
}
