package com.main.app.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class SubmitContactEmailServiceImpl implements SubmitContactEmailService {

    private EmailClient emailClient;

    private MailContentBuilder mailContentBuilder;

    @Value("${spring.mail.username}")
    private String email;

    @Autowired
    public SubmitContactEmailServiceImpl(EmailClient emailClient, MailContentBuilder mailContentBuilder) {
        this.emailClient = emailClient;
        this.mailContentBuilder = mailContentBuilder;
    }

    @Override
    @Async("processExecutor")
    public void sendEmail(String name, String emailFrom, String city, String subject, String message) {

        String createdMessage = "Name: " + name  + "\nEmail: " + emailFrom + "\n" + "City: " + city + "\n" + "Message: " + message;

        String content = mailContentBuilder.buildContentWithLink("", createdMessage, "");

        emailClient.sendMimeEmail(
                email,
                email,
                subject,
                content
        );
    }
}