package com.rezervasyon.rezervasyon_sistemi.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("simsekkibrahim41@gmail.com");  // Gönderici adresin
        message.setTo(to);                          // Alıcı
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}

