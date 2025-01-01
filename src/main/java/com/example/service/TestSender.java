package com.example.service;

import com.example.domain.User;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.ArrayList;
import java.util.List;

public class TestSender implements MailSender {
    List<String> requests = new ArrayList<>();
    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {
    }
    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {
        SimpleMailMessage message = simpleMessage;
        String request = message.getTo()[0];
        requests.add(request);
    }

    public List<String> getRequests() {
        return requests;
    }


}
