package com.pharmaflow.demo.Mappers;

import com.pharmaflow.demo.Dto.EditUser;
import com.pharmaflow.demo.Dto.UserDto;
import com.pharmaflow.demo.Dto.UserRegister;
import com.pharmaflow.demo.Entities.User;
import com.pharmaflow.demo.Enums.Role;
import org.mapstruct.*;

import java.lang.annotation.Target;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", source = "role")
    @Mapping(target = "active", source = "active")
    UserDto toDto(User user);

    @Mapping(target = "role", source = "userRegister.role", qualifiedByName = "stringToRole")
    User toEntity(UserRegister userRegister);

    @Named("stringToRole")
    default Role fromStringToRole(String role) {
        if (role == null)
            return null;
        return Role.valueOf(role.toUpperCase().trim());
    }

    List<UserDto> toDto(List<User> users);
    List<User> toEntity(List<UserDto> usersDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void editDtoToEntity(EditUser editUser, @MappingTarget User user);
}
