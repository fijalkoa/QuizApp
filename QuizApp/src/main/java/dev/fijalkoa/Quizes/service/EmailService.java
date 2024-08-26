package dev.fijalkoa.Quizes.service;

import jakarta.mail.MessagingException;

public interface EmailService {

    void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException;
    void sendSimpleMessage(String to, String subject, String text);
}
