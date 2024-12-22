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
        User user = null;
        if(rs.next()){
            user = new User();
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
        };
        rs.close();
        ps.close();
        c.close();
        if(user==null){
            throw new SQLException("User not found");
        }
        return user;
    }
    public void deleteAll() throws ClassNotFoundException, SQLException {
        Connection c =null;
        PreparedStatement ps = null;
        try{
            c = connectionCreator.getConnection();
            ps = c.prepareStatement("delete from users");
            ps.executeUpdate();
        } catch (ClassNotFoundException e) {
            throw e;
        } catch (SQLException e) {
            throw e;
        }
        finally{
            if(ps != null){
                try{
                    ps.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
            if(c != null){
                try{
                    c.close();
                }
                catch (SQLException e) {
                    throw e;
                }
            }

        }
    }
    public int getCount() throws ClassNotFoundException, SQLException {
        Connection c =null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            c = connectionCreator.getConnection();
            ps = c.prepareStatement("select count(*) from users");
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (ClassNotFoundException e) {
            throw e;
        }
        catch(SQLException e){
            throw e;
        }
        finally{
            if(ps != null){
                try{
                    ps.close();
                }
                catch (SQLException e) {
                    throw e;
                }
            }
            if(c != null){
                try{
                    c.close();

                }
                catch (SQLException e) {
                    throw e;
                }
            }
            if(rs != null){
                try {
                    rs.close();
                }
                catch (SQLException e) {
                    throw e;
                }
            }
        }
    }
}
