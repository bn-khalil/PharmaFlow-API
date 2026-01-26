package com.pharmaflow.demo.Services.Impl;

import com.pharmaflow.demo.Services.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendWelcomingToNewMembers(String to, String subject, String body) throws MessagingException {
        MimeMessage message = this.javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);
        this.javaMailSender.send(message);
    }
}
