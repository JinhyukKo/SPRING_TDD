package com.example;

import com.example.domain.User;
import com.example.domain.UserDao;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args)  throws ClassNotFoundException, SQLException {
        System.out.println("Hello world!");
        User newUser = new User();
        UserDao userDao = new UserDao();
        newUser.setPassword("password");
        newUser.setUsername("username");
        userDao.add(newUser);

        User user = userDao.get(newUser.getUsername());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
    }
}