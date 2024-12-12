package com.example.domain;


import java.sql.*;

public class UserDao {
    private ConnectionCreator connectionCreator;
    public UserDao (ConnectionCreator connectionCreator) {
        this.connectionCreator = connectionCreator;
    }
    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = connectionCreator.getConnection();
        PreparedStatement ps = c.prepareStatement("insert into users(username,password) values(?,?)");
        ps.setString(1,user.getUsername());
        ps.setString(2,user.getPassword());
        ps.executeUpdate();
        ps.close();
        c.close();
    }
    public User get(String username) throws ClassNotFoundException, SQLException{
        Connection c = connectionCreator.getConnection();
        PreparedStatement ps = c.prepareStatement("select * from users where username = ?");
        ps.setString(1,username);
        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        rs.close();
        ps.close();
        c.close();
        return user;
    }
}
