package com.pharmaflow.demo.Listeners;

import com.pharmaflow.demo.Events.NotificationEvent;
import com.pharmaflow.demo.Services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class NotificationListener {
    private final NotificationService notificationService;

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Async
    public void sendNotification(NotificationEvent notificationEvent) {
        this.notificationService.createNotification(
                notificationEvent.message(),
                notificationEvent.notif(),
                notificationEvent.product()
        );
    }
}
