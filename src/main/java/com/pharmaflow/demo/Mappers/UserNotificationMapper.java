package com.pharmaflow.demo.Mappers;

import com.pharmaflow.demo.Dto.UserNotificationDto;
import com.pharmaflow.demo.Entities.Notification;
import com.pharmaflow.demo.Entities.UserNotification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserNotificationMapper {
    @Mapping(source = "notification.status", target = "status")
    @Mapping(source = "notification.product.id", target = "productId")
    @Mapping(source = "notification.product.name", target = "productName")
    UserNotificationDto toDto(UserNotification userNotification);
    List<UserNotificationDto> toDto(List<UserNotification> userNotifications);
}
