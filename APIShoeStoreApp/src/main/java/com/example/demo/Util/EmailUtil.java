package com.example.demo.Util;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailUtil {

    private final String EMAIL_SENDER;
    private final String EMAIL_PASSWORD;

    private static final String SMTP_HOST = "smtp.gmail.com"; // SMTP server của Gmail
    private static final int SMTP_PORT = 587; // Cổng SMTP

    public EmailUtil(@Value("${spring.mail.username}") String emailSender,
                     @Value("${spring.mail.password}") String emailPassword) {
        this.EMAIL_SENDER = emailSender;
        this.EMAIL_PASSWORD = emailPassword;
    }


    public void sendOTP(String email, String otp) {
        // Cấu hình các thuộc tính cho SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", String.valueOf(SMTP_PORT));

        // Xác thực email gửi đi
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_SENDER, EMAIL_PASSWORD);
            }
        });

        try {
            // Tạo nội dung email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_SENDER)); // Email người gửi
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email)); // Email người nhận
            message.setSubject("Mã OTP của bạn"); // Tiêu đề email
            message.setText("Xin chào,\n\nMã OTP của bạn là: " + otp +
                    "\nVui lòng không chia sẻ mã này với bất kỳ ai.\n\nTrân trọng!"); // Nội dung email

            // Gửi email
            Transport.send(message);
            System.out.println("Email đã được gửi tới: " + email);
        } catch (MessagingException e) {
            System.err.println("Lỗi khi gửi email: " + e.getMessage());
        }
    }
}

