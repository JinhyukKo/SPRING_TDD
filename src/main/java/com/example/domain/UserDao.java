package com.example.domain;

import java.util.List;

public interface UserDao {
public void add(User user);
public User get(String username);
public void deleteAll();
public List<User> getAll();
public int getCount();
}
