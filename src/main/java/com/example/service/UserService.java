package com.example.service;

import com.example.domain.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserService {
    void upgradeLevels();

    void add(User user);
    void deleteAll();
    void update(User user);
    User get(String username);
    int getCount();
    List<User> getAll();
}
