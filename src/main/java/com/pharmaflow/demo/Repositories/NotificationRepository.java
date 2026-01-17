package com.pharmaflow.demo.Repositories;

import com.pharmaflow.demo.Entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    //should return notifications only for last 20 day
    List<Notification> findAll();
}
