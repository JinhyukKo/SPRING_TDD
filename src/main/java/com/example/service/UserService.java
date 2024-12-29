package com.example.service;

import com.example.domain.Level;
import com.example.domain.User;
import com.example.domain.UserDao;

import java.util.List;

public class UserService {
    UserDao userDao;
    UpgradePolicy upgradePolicy;

    public UserService(UserDao userDao, UpgradePolicy upgradePolicy) {
        this.userDao = userDao;
        this.upgradePolicy = upgradePolicy;
    }

    public void upgradeLevels() {
        List<User> users = userDao.getAll();
        for (User user : users) {
            if (upgradePolicy.isUpgradable(user))
                upgradeLevel(user);
        }
    }


    private void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
    }

    public void add(User user) {
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }
}
