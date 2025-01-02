package com.example.service.upgrade;

import com.example.domain.User;

public interface UpgradePolicy {
    boolean isUpgradable(User user);
}
