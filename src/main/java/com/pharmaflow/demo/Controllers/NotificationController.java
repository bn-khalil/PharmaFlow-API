package com.pharmaflow.demo.Controllers;

import com.pharmaflow.demo.Dto.UserNotificationDto;
import com.pharmaflow.demo.Services.UserNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final UserNotificationService userNotificationService;

    @GetMapping
    public ResponseEntity<List<UserNotificationDto>> getMyNotifications() {
        return ResponseEntity.ok().body(this.userNotificationService.getNotificationsForUser());
    }

    @PostMapping("/{notificationId}/read")
    public ResponseEntity<Void> readNotification(@PathVariable UUID notificationId) {
        this.userNotificationService.markNotificationRead(notificationId);
        return ResponseEntity.noContent().build();
    }
}
