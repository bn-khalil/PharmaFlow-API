package com.pharmaflow.demo.Repositories;

import com.pharmaflow.demo.Entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
}
