package com.pharmaflow.demo.Events;

import com.pharmaflow.demo.Enums.Action;

public record AuditCreatedEvent (
        String productName,
        long quantity,
        Action action,
        long before,
        long after
){ }

