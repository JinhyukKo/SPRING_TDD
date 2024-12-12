package com.example.domain;

public class DaoFactory {
    ConnectionCreator connectionCreator = new ConnectionCreatorImpl();
    public UserDao getUserDao() {
        UserDao userDao = new UserDao(connectionCreator);
        return userDao;
    }
}
