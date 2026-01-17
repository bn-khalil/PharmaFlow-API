package com.pharmaflow.demo.Services;

import com.pharmaflow.demo.Entities.Notification;
import com.pharmaflow.demo.Entities.Product;
import com.pharmaflow.demo.Enums.Notify;

public interface NotificationService {
    public void createNotification(String message, Notify notify, Product product);
    public void broadCastToAllUsers(Notification notification);
}
