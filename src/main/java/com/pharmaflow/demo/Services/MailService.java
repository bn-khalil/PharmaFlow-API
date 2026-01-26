package com.pharmaflow.demo.Services;

import jakarta.mail.MessagingException;

public interface MailService {
    void sendWelcomingToNewMembers(String to, String subject, String body) throws MessagingException;
}
