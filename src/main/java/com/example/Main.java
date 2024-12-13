package com.example;

import com.example.domain.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args)  throws ClassNotFoundException, SQLException {
        System.out.println(System.getProperty("java.class.path"));
        User newUser = new User();
//        UserDao userDao = new DaoFactory().getUserDao();

        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao userDao = context.getBean("userDao", UserDao.class);
        CountingConnectionCreatorDecorator connectionCreator = context.getBean("connectionCreator", CountingConnectionCreatorDecorator.class);
        System.out.println(connectionCreator.getCount());

        newUser.setPassword("password");
        newUser.setUsername("username");
        userDao.add(newUser);

        User user = userDao.get(newUser.getUsername());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(connectionCreator.getCount());
    }
}