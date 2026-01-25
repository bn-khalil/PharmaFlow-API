package com.pharmaflow.demo.Events;

import com.pharmaflow.demo.Entities.Product;
import com.pharmaflow.demo.Enums.Notify;

public record NotificationEvent(
        String message,
        Notify notif,
        Product product
) {}
