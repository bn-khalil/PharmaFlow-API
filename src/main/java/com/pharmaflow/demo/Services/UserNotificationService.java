package com.pharmaflow.demo.Services;

import com.pharmaflow.demo.Dto.UserNotificationDto;
import com.pharmaflow.demo.Entities.User;

import java.util.List;

public interface UserNotificationService {
    List<UserNotificationDto> getNotificationsForUser();
}
