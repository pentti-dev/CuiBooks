package com.example.mobileapi.service;

import com.example.mobileapi.exception.AppException;

public interface EmailService {
    void sendPasswordResetEmail(String to, String subject, String text) throws AppException;

}
