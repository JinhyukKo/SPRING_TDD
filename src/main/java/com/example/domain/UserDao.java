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
    public void deleteAll() throws ClassNotFoundException, SQLException {
        Connection c = connectionCreator.getConnection();
        PreparedStatement ps = c.prepareStatement("delete from users");
        ps.executeUpdate();
        ps.close();
        c.close();
    }
    public int getCount() throws ClassNotFoundException, SQLException {
        Connection c = connectionCreator.getConnection();
        PreparedStatement ps = c.prepareStatement("select count(*) from users");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        rs.close();
        ps.close();
        c.close();
        return count;
    }
}
