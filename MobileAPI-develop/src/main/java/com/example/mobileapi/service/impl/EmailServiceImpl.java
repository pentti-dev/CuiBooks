package com.example.mobileapi.service.impl;

import com.example.mobileapi.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender emailSender;



    @Override
    public void sendPasswordResetEmail(String to, String subject, String text) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setText(text, true); // HTML content
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom("mobileapp084@gmail.com"); // Thay bằng email thực tế của bạn

            log.info("Sending email to: {}", to);
            log.info("Email subject: {}", subject);
            log.info("Email text: {}", text);

            emailSender.send(message);

            log.info("Email sent successfully to: {}", to);
        } catch (MessagingException e) {
            log.error("Error sending email to: {}", to, e);
            throw new MailParseException(e);
        }
    }
}
