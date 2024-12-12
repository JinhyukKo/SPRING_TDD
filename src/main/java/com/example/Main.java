package com.example;

import com.example.domain.*;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args)  throws ClassNotFoundException, SQLException {
        System.out.println(System.getProperty("java.class.path"));
        User newUser = new User();
        UserDao userDao = new DaoFactory().getUserDao();

        newUser.setPassword("password");
        newUser.setUsername("username");
        userDao.add(newUser);

        User user = userDao.get(newUser.getUsername());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
    }
}