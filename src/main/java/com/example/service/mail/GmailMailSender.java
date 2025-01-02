package com.example.service.mail;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class GmailMailSender implements MailSender {

    JavaMailSenderImpl mailSender;

    public GmailMailSender(){
        mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("kobin1970@gmail.com");
        mailSender.setPassword(System.getenv("GMAIL_APP_PASSWORD")); // Use an app-specific password

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        mailSender.setJavaMailProperties(props);
    }
    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {
        mailSender.send(simpleMessages);
    }
}
