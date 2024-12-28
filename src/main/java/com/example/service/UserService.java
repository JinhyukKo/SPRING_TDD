package com.example.service;

import com.example.domain.Level;
import com.example.domain.User;
import com.example.domain.UserDao;

import java.util.List;

public class UserService {
    UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void upgradeLevels() {
        List<User> users = userDao.getAll();
        boolean changed = false;
        for (User user : users) {
            switch (user.getLevel()) {
                case BASIC:
                    if (user.getLogin() >= 50) user.setLevel(Level.SILVER);
                    changed = true;
                    break;
                case SILVER:
                    if (user.getRecommend() >= 30) user.setLevel(Level.GOLD);
                    changed = true;
                    break;
                default:
                    break;
            }
            if(changed) userDao.update(user);
        }
    }
}
