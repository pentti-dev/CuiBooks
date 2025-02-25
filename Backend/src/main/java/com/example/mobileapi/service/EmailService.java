package com.example.mobileapi.service;

public interface EmailService {
    void sendPasswordResetEmail(String to, String subject, String text);

}
