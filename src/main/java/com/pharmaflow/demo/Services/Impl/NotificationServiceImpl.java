package com.pharmaflow.demo.Services.Impl;

import com.pharmaflow.demo.Entities.Notification;
import com.pharmaflow.demo.Entities.Product;
import com.pharmaflow.demo.Entities.User;
import com.pharmaflow.demo.Entities.UserNotification;
import com.pharmaflow.demo.Enums.Notify;
import com.pharmaflow.demo.Repositories.NotificationRepository;
import com.pharmaflow.demo.Repositories.ProductRepository;
import com.pharmaflow.demo.Repositories.UserNotificationRepository;
import com.pharmaflow.demo.Repositories.UserRepository;
import com.pharmaflow.demo.Services.NotificationService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final UserNotificationRepository userNotificationRepository;

    @Override
    @Transactional
    public void createNotification(String message, Notify notify, Product product) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setStatus(notify);
        notification.setProduct(product);

        notification = this.notificationRepository.save(notification);
        broadCastToAllUsers(notification);
    }

    @Override
    public void broadCastToAllUsers(Notification notification) {
        List<User> recipients = this.userRepository.findAll();
        for (User recipient: recipients) {
            UserNotification userNotification = new UserNotification();
            userNotification.setUser(recipient);
            userNotification.setNotification(notification);
            userNotification.setIsRead(false);

            this.userNotificationRepository.save(userNotification);
        }
        // send notification to front end with websocket or kafka
    }
}
