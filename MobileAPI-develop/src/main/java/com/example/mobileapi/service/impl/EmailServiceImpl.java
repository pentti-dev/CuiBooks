package com.example.mobileapi.service.impl;

import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.exception.ErrorCode;
import com.example.mobileapi.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender emailSender;

    @Override
    @Async
    public void sendPasswordResetEmail(String to, String subject, String text) throws AppException {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            // Sử dụng MimeMessageHelper để hỗ trợ nội dung HTML
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // Xây dựng nội dung HTML cho email
            String htmlContent = "<html>" +
                    "<body style='font-family: Arial, sans-serif;'>" +
                    "<h2 style='color: #2E8B57;'>Đặt lại mật khẩu của bạn</h2>" +
                    "<p>Chúng tôi đã nhận được yêu cầu đặt lại mật khẩu cho tài khoản của bạn.</p>" +
                    "<p><strong>Thông tin tài khoản:</strong></p>" +
                    "<ul>" +
                    "<li><strong>Email:</strong> " + to + "</li>" +
                    "</ul>" +
                    "<p>Đây là mã để đặt lại mật khẩu:</p>" +
                    "<h3 style='color: #2E8B57;'>" + text + "</h3>" +
                    "<p>Nếu bạn không yêu cầu thay đổi mật khẩu, vui lòng bỏ qua email này.</p>" +
                    "<hr>" +
                    "<p>Trân trọng,<br>Đội ngũ hỗ trợ</p>" +
                    "</body>" +
                    "</html>";

            // Thiết lập các thông tin cho email
            helper.setText(htmlContent, true); // true -> HTML content
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom("mobileapp084@gmail.com"); // Thay bằng email thực tế của bạn

            log.info("Sending email to: {}", to);
            log.info("Email subject: {}", subject);

            // Gửi email
            emailSender.send(message);

            log.info("Email sent successfully to: {}", to);
        } catch (MessagingException e) {
            log.error("Error sending email to: {}", to, e);
            throw new AppException(ErrorCode.ERROR_DURING_SEND_EMAIL);
        }
    }

}
