package org.thekiddos.manager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl {
    private JavaMailSender emailSender;

    @Autowired
    public EmailServiceImpl( JavaMailSender emailSender ) {
        this.emailSender = emailSender;
    }

    public void sendEmail( String to, String subject, String text ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply.kdrs@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
