package com.pharmaflow.demo.Services;

import com.pharmaflow.demo.Dto.UserNotificationDto;
import com.pharmaflow.demo.Entities.User;

import java.util.List;
import java.util.UUID;

public interface UserNotificationService {
    List<UserNotificationDto> getNotificationsForUser();
    void markNotificationRead(UUID notificationId);
    void markAllNotificationRead();
    public List<UserNotificationDto> getUnreadNotificationsForUser();
}
