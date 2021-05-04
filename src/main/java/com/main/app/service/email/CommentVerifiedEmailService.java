package com.main.app.service.email;

public interface CommentVerifiedEmailService {

    void sendEmail(String url, String pathParam, String emailFrom, String emailTo, String urlPart);

}
