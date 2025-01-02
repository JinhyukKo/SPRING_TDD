package com.example.service;

import com.example.domain.User;

public interface UserService {
    void upgradeLevels();

    void add(User user);
}
