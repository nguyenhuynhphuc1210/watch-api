package com.example.watch.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtp(String toEmail, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toEmail);
        message.setSubject("Mã OTP đặt lại mật khẩu");
        message.setText(
                "Xin chào,\n\n"
                + "Mã OTP của bạn là: " + otp + "\n"
                + "OTP có hiệu lực trong 5 phút.\n\n"
                + "Vui lòng không chia sẻ mã này cho bất kỳ ai."
        );

        mailSender.send(message);
    }
}
