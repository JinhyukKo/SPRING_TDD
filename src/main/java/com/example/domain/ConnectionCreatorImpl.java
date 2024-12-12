package com.example.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionCreatorImpl implements ConnectionCreator {
    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tobi", "root", "password");
        return connection;
    }
}
