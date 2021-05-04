package com.main.app.service.email;

public interface CommentRemovedEmailService {
    void sendEmail(String emailFrom, String emailTo,String commentDescription);
}
