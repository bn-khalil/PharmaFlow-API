package com.pharmaflow.demo.Events;

import com.pharmaflow.demo.Entities.Product;
import com.pharmaflow.demo.Enums.Action;
import com.pharmaflow.demo.Enums.Notify;

public record AuditCreatedEvent (
        String productName,
        long quantity,
        Action action,
        long before,
        long after
){ }

