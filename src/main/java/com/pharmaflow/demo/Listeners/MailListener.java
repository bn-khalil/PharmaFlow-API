package com.pharmaflow.demo.Listeners;

import com.pharmaflow.demo.Events.MailSendWelcomeEvet;
import com.pharmaflow.demo.Services.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailListener {

    private final MailService mailService;

    @Async
    @EventListener
    public void sendWelcomeTo(MailSendWelcomeEvet mailSendWelcomeEvet) {
        String subject = "Welcome to Pharma-Flow";
        String html = "Hi there " + mailSendWelcomeEvet.name() +
                " 👋!</h1><p> You are know a member and a part of Pharma-Flow as "+
                mailSendWelcomeEvet.role() +", Welcome again.</p>";

        try {
            this.mailService.sendWelcomingToNewMembers(
                    mailSendWelcomeEvet.to(),
                    subject,
                    html
            );
        } catch (Exception e) {
            log.error("❌ Failed to send welcome email to {}: {}", mailSendWelcomeEvet.to(), e.getMessage());
        }
    }

}
