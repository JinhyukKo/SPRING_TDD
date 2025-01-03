package com.example.domain;

import java.util.List;

public interface UserDao {
    void add(User user);

    User get(String username);

    void deleteAll();

    List<User> getAll();

    int getCount();

    void update(User user);
}
