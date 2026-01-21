package com.pharmaflow.demo.Mappers;

import com.pharmaflow.demo.Dto.UserNotificationDto;
import com.pharmaflow.demo.Entities.UserNotification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserNotificationMapper {
    @Mapping(source = "notification.status", target = "status")
    @Mapping(source = "notification.product.id", target = "productId")
    @Mapping(source = "notification.product.name", target = "productName")
    @Mapping(source = "notification.message", target = "message")
    UserNotificationDto toDto(UserNotification userNotification);
    List<UserNotificationDto> toDto(List<UserNotification> userNotifications);
}
