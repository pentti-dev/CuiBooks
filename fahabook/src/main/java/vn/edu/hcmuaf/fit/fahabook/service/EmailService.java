package vn.edu.hcmuaf.fit.fahabook.service;

import vn.edu.hcmuaf.fit.fahabook.exception.AppException;

public interface EmailService {
    void sendPasswordResetEmail(String to, String subject, String text) throws AppException;
}
