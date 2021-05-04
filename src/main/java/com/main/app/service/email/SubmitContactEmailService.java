package com.main.app.service.email;


public interface SubmitContactEmailService {

    void sendEmail(String name, String emailFrom, String city, String subject, String message);
}