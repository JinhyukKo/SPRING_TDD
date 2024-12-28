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
        for (User user : users) {
            if (canUpgradeLevel(user))
                upgradeLevel(user);
        }
    }

    private boolean canUpgradeLevel(User user) {
        switch (user.getLevel()) {
            case BASIC:
                return user.getLogin() >= 50;
            case SILVER:
                return user.getRecommend()>=30;
            case GOLD:
                return false;
            default:
                throw new IllegalArgumentException("Unsupported level: " + user.getLevel());
        }
    }

    private void upgradeLevel(User user) {
        if (user.getLevel() == Level.BASIC) {
            user.setLevel(Level.SILVER);
        } else if (user.getLevel() == Level.SILVER) {
            user.setLevel(Level.GOLD);
        }
        userDao.update(user);
    }

    public void add(User user) {
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }
}
