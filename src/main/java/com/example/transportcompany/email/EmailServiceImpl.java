package com.example.transportcompany.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailServiceImpl {
    private final JavaMailSender emailSender;


    public void sendCreateAccountMessage(String to, String username, String password,String company){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("mt.transport.company@gmail.com");
        message.setTo(to);
        message.setSubject("Welcome to transport company");
        message.setText("your username: "+username+"\nyour password: "+password+"\nyour company: "+company);
        emailSender.send(message);
    }
}