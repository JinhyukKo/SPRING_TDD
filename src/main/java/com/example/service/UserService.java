package com.example.service;

import com.example.domain.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {
    void upgradeLevels();

    void add(User user);
}
