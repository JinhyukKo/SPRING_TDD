package com.example.service;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class TestSender implements MailSender {
    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {

    }
}
