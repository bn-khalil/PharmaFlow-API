package com.pharmaflow.demo.Services.Impl;

import com.pharmaflow.demo.Dto.NotificationDto;
import com.pharmaflow.demo.Repositories.NotificationRepository;
import com.pharmaflow.demo.Services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public List<NotificationDto> getAllNotification() {
        return List.of();
    }
}
