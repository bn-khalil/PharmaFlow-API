package com.pharmaflow.demo.Repositories;

import com.pharmaflow.demo.Entities.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface UserNotificationRepository extends JpaRepository<UserNotification, UUID> {
    @Query("""
    select un from UserNotification un
    where un.user.id = :userId
    AND un.isRead = false
    order by createdAt
    desc
    """)
    List<UserNotification> findMyNotifications(@Param("userId") UUID userId);

    @Query("""
    select un from UserNotification un
    where un.user.id = :userId
    order by createdAt
    desc
    """)
    List<UserNotification> findMyUnreadNotifications(@Param("userId") UUID userId);
}
