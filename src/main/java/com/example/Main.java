package com.example;

import com.example.domain.User;
import com.example.domain.UserDao;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        User user = new User();
        UserDao userDao = new UserDao();
    }
}