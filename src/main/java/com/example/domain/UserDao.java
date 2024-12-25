package com.example.domain;


import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserDao {
    private ConnectionCreator connectionCreator;
    private JdbcTemplate jdbcTemplate;

    public UserDao(ConnectionCreator connectionCreator, JdbcTemplate jdbcTemplate) {
        this.connectionCreator = connectionCreator;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
//        jdbcContextWithStatement((connection)->{
//            PreparedStatement ps = connection.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
//            ps.setString(1, user.getUsername());
//            ps.setString(2, user.getPassword());
//            return ps;
//        });
        jdbcTemplate.update("INSERT INTO users (username,password) VALUES (?,?)"
                , user.getUsername(),
                user.getPassword());
    }

    public User get(String username) throws ClassNotFoundException, SQLException {
//        Connection c = connectionCreator.getConnection();
//        PreparedStatement ps = c.prepareStatement("select * from users where username = ?");
//        ps.setString(1, username);
//        ResultSet rs = ps.executeQuery();

//        if (rs.next()) {
//            user = new User();
//            user.setUsername(rs.getString("username"));
//            user.setPassword(rs.getString("password"));
//        }
//        rs.close();
//        ps.close();
//        c.close();
//        if (user == null) {
//            throw new SQLException("User not found");
//        }
        User user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE username = ?", new Object[]{username}, (ResultSet rs,int rowNum)->{
            User newUser =new User();
            newUser.setUsername(rs.getString("username"));
            newUser.setPassword(rs.getString("password"));
            return newUser;
        });
        return user;
    }
    public List<User> getAll() throws ClassNotFoundException, SQLException {
        List<User> users = jdbcTemplate.query("SELECT * FROM users", (ResultSet rs,int rowNum)->{
            User user = new User();
            user.setPassword(rs.getString("password"));
            user.setUsername(rs.getString("username"));
            return user;
        });
        return users;
    }

    public void deleteAll() throws ClassNotFoundException, SQLException {
//        jdbcContextWithStatement((connection)->{
//            return connection.prepareStatement("DELETE FROM users");
//        }
        jdbcTemplate.update("DELETE FROM users");
    }

    public int getCount() throws ClassNotFoundException, SQLException {
//        Connection c = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        try {
//            c = connectionCreator.getConnection();
//            ps = c.prepareStatement("select count(*) from users");
//            rs = ps.executeQuery();
//            rs.next();
//            return rs.getInt(1);
//        } catch (ClassNotFoundException e) {
//            throw e;
//        } catch (SQLException e) {
//            throw e;
//        } finally {
//            if (ps != null) {
//                try {
//                    ps.close();
//                } catch (SQLException e) {
//                    throw e;
//                }
//            }
//            if (c != null) {
//                try {
//                    c.close();
//
//                } catch (SQLException e) {
//                    throw e;
//                }
//            }
//            if (rs != null) {
//                try {
//                    rs.close();
//                } catch (SQLException e) {
//                    throw e;
//                }
//            }
//        }
        return jdbcTemplate.query("select count(*) from users",(ResultSet rs)->{
            rs.next();
            return rs.getInt(1);
        });

    }

    public void jdbcContextWithStatement(StatementStrategy stmt) throws SQLException, ClassNotFoundException {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = connectionCreator.getConnection();
            ps = stmt.makePreparedStatement(c);
            ps.executeUpdate();
        } catch (ClassNotFoundException e) {
            throw e;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }
}
