package com.main.app.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

/**
 * The client for sending emails.
 *
 * @author simona
 */
@Service
public class EmailClient {

    private JavaMailSender javaMailSender;

    @Autowired
    public EmailClient(JavaMailSenderImpl javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMimeEmail(String emailFrom, String emailTo, String subject, String content) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

            messageHelper.setText(content, true);
            messageHelper.setFrom(emailFrom);
            messageHelper.setTo(emailTo);
            messageHelper.setSubject(subject);
        };
        try {
            javaMailSender.send(messagePreparator);
        } catch (MailException e) {
            // runtime exception; compiler will not force you to handle it
        }
    }
}
