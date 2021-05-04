package com.main.app.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class CommentRemovedEmailServiceImpl implements CommentRemovedEmailService{

    private static final String EMAIL_SUBJECT = "Comment Removed!";


    private static final String MESSAGE_BEFORE =
                    "Hi, you recently commented on some product from our inventory."
                    + "Your comment contains parts that are not allowed under our business policies."
                    + "So in that case our Administrative Team was forced to remove this comment.";

    private static final String MESSAGE_AFTER =
            "If you did not understand why we removed your comment,please contact us through our site.";

    private EmailClient emailClient;

    private MailContentBuilder mailContentBuilder;

    @Autowired
    public CommentRemovedEmailServiceImpl(EmailClient emailClient, MailContentBuilder mailContentBuilder) {
        this.emailClient = emailClient;
        this.mailContentBuilder = mailContentBuilder;
    }


    @Override
    @Async("processExecutor")
    public void sendEmail(String emailFrom, String emailTo,String commentDescription) {

        String content = mailContentBuilder.buildContentWithLink(commentDescription, MESSAGE_BEFORE, MESSAGE_AFTER);

        emailClient.sendMimeEmail(
                emailFrom,
                emailTo,
                EMAIL_SUBJECT,
                content
        );

    }
}
