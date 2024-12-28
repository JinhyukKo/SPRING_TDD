package com.example.service;

import com.example.domain.UserDao;

public class UserService {
    UserDao userDao;
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
}
