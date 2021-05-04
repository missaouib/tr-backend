package com.main.app.service.email;

import com.main.app.static_data.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class PasswordRecoveryEmailServiceImpl implements PasswordRecoveryEmailService {

    private static final String EMAIL_SUBJECT = "Forgot Password";

    private static final String MESSAGE_BEFORE =
            "Hi, you recently requested to reset your password. Click the link below to reset it.";

    private static final String MESSAGE_AFTER =
            "If you did not requested a password reset, please ignore this email or let us know." +
                    "  This password reset is valid for next " + Constants.VALIDITY_OF_TOKEN_IN_DAYS + " days.";

    private EmailClient emailClient;

    private MailContentBuilder mailContentBuilder;

    @Autowired
    public PasswordRecoveryEmailServiceImpl(EmailClient emailClient, MailContentBuilder mailContentBuilder) {
        this.emailClient = emailClient;
        this.mailContentBuilder = mailContentBuilder;
    }

    @Override
    @Async("processExecutor")
    public void sendEmail(String url, String pathParam, String emailFrom, String emailTo, String urlPart) {
        String link = url + urlPart + pathParam;

        String content = mailContentBuilder.buildContentWithLink(link, MESSAGE_BEFORE, MESSAGE_AFTER);

        emailClient.sendMimeEmail(
                emailFrom,
                emailTo,
                EMAIL_SUBJECT,
                content
        );
    }
}
