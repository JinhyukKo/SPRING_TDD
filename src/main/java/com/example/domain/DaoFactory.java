package com.example.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {

    @Bean
    public UserDao userDao() {
        UserDao userDao = new UserDao(connectionCreator());
        return userDao;
    }
    @Bean
    public ConnectionCreator connectionCreator() {
        return new CountingConnectionCreatorDecorator(realConnectionCreator());
    }
    @Bean
    public ConnectionCreator realConnectionCreator (){
        return new ConnectionCreatorImpl();
    }
}
