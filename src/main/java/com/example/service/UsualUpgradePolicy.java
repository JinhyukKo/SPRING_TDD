package com.example.service;

import com.example.domain.User;

public class UsualUpgradePolicy implements  UpgradePolicy {


    @Override
    public boolean isUpgradable(User user) {
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
}
