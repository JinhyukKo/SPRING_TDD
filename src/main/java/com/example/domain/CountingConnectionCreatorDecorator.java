package com.example.domain;

import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionCreatorDecorator implements ConnectionCreator  {

    private int count =0;
    private ConnectionCreator connectionCreator;
    public CountingConnectionCreatorDecorator(ConnectionCreator connectionCreator) {
        this.connectionCreator = connectionCreator;
    }
    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        count++;
        return connectionCreator.getConnection();
    }
    public int getCount() {
        return count;
    }
}
