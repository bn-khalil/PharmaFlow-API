package com.pharmaflow.demo.Listeners;

import com.pharmaflow.demo.Events.AuditCreatedEvent;
import com.pharmaflow.demo.Services.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class AuditListener {

    private final AuditService auditService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Async
    public void auditCreated(AuditCreatedEvent auditCreatedEvent) {
        this.auditService.createAudit(
                auditCreatedEvent.productName(),
                auditCreatedEvent.quantity(),
                auditCreatedEvent.action(),
                auditCreatedEvent.before(),
                auditCreatedEvent.after()
        );
    }
}