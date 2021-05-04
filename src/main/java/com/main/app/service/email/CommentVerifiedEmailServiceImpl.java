package com.main.app.service.email;

import com.main.app.static_data.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class CommentVerifiedEmailServiceImpl implements CommentVerifiedEmailService{

    private static final String EMAIL_SUBJECT = "Comment Verified!";

    private static final String MESSAGE_BEFORE =
            "Hi, you recently requested to comment on some product from our inventory."
                    + "Your comment has been published by our Administration team."
                    + " Click the link below to see your comment.";

    private static final String MESSAGE_AFTER =
            "If you did not requested a verify comment, please ignore this email or let us know.";

    private EmailClient emailClient;

    private MailContentBuilder mailContentBuilder;

    @Autowired
    public CommentVerifiedEmailServiceImpl(EmailClient emailClient, MailContentBuilder mailContentBuilder) {
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
