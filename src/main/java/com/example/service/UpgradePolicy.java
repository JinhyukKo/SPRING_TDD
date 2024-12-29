package com.example.service;

import com.example.domain.User;

public interface UpgradePolicy {
    boolean isUpgradable(User user);
}
