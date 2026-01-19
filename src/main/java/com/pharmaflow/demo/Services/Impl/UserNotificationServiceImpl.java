package com.pharmaflow.demo.Services.Impl;

import com.pharmaflow.demo.Dto.UserNotificationDto;
import com.pharmaflow.demo.Entities.User;
import com.pharmaflow.demo.Entities.UserNotification;
import com.pharmaflow.demo.Exceptions.ResourceNotFoundException;
import com.pharmaflow.demo.Mappers.UserNotificationMapper;
import com.pharmaflow.demo.Repositories.UserNotificationRepository;
import com.pharmaflow.demo.Security.UserSecurity;
import com.pharmaflow.demo.Services.UserNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserNotificationServiceImpl implements UserNotificationService {
    private final UserNotificationRepository userNotificationRepository;
    private final UserNotificationMapper userNotificationMapper;

    @Override
    @Transactional(readOnly = true)
    public List<UserNotificationDto> getNotificationsForUser() {
        UserSecurity userSecurity = (UserSecurity)SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userSecurity.getUser();
        List<UserNotification> userNotifications = userNotificationRepository
                .findAllByUserId(user.getId());
        return this.userNotificationMapper.toDto(userNotifications);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserNotificationDto> getUnreadNotificationsForUser() {
        UserSecurity userSecurity = (UserSecurity)SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userSecurity.getUser();
        List<UserNotification> userNotifications = userNotificationRepository
                .findMyUnreadNotifications(user.getId());
        return this.userNotificationMapper.toDto(userNotifications);
    }

    @Override
    @Transactional
    public void markNotificationRead(UUID notificationId) {
        UserNotification userNotification = this.userNotificationRepository
                .findById(notificationId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Notification Not Found")
        );
        userNotification.setIsRead(true);
    }

    @Override
    @Transactional
    public void markAllNotificationRead() {
        UserSecurity userSecurity = (UserSecurity)SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userSecurity.getUser();
        List<UserNotification> userNotifications = userNotificationRepository
                .findMyUnreadNotifications(user.getId());
        userNotifications.forEach(userNotification -> userNotification.setIsRead(true));
    }
}
