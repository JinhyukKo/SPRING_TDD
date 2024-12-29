package com.example.service;

import com.example.domain.User;

public class UsualUpgradePolicy implements  UpgradePolicy {

final static int MIN_LOGIN_COUNT_FOR_SILVER = 50;
final static int MIN_RECOMMEND_COUNT_FOR_GOLD= 30;
    @Override
    public boolean isUpgradable(User user) {
        return switch (user.getLevel()) {
            case BASIC -> user.getLogin() >= MIN_LOGIN_COUNT_FOR_SILVER;
            case SILVER -> user.getRecommend()>=MIN_RECOMMEND_COUNT_FOR_GOLD;
            case GOLD -> false;
            default -> throw new IllegalArgumentException("Unsupported level: " + user.getLevel());
        };
    }
}
