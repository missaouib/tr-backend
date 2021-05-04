package com.main.app.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * The implementation of EmailService used for sending email to drivers for registration.
 *
 * @author Nikola
 *
 */
@Service
public class RegistrationEmailServiceImpl implements RegistrationEmailService {

    private static final String EMAIL_SUBJECT = "Registration";

    private static final String MESSAGE_BEFORE =
            "Hi, your contract is saved by our admin team. You can register on link bellow.";

    private static final String MESSAGE_AFTER =
            "";

    private EmailClient emailClient;

    private MailContentBuilder mailContentBuilder;

    @Autowired
    public RegistrationEmailServiceImpl(EmailClient emailClient, MailContentBuilder mailContentBuilder) {
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
