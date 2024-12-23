package com.example.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddStatement implements StatementStrategy{

    User user;
    public AddStatement(User user){
        this.user = user;
    }
    @Override
    public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
        PreparedStatement ps = c.prepareStatement("insert into users(username,password) values(?,?)");
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getPassword());
        return ps;
    }
}
