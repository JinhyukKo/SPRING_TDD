package com.example.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface ConnectionCreator {
    public Connection getConnection() throws ClassNotFoundException, SQLException;
}
