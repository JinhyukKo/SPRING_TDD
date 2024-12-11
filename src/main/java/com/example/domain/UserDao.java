package com.example.domain;


import java.sql.*;

public class UserDao {
    public void add(User user) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/tobi","root","hyuk5907");
        PreparedStatement ps = c.prepareStatement("insert into users(username,password) values(?,?)");
        ps.setString(1,user.getUsername());
        ps.setString(1,user.getPassword());
        ps.executeUpdate();
        ps.close();
        c.close();
    }
    public User get(String username) throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/tobi","root","hyuk5907");
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
