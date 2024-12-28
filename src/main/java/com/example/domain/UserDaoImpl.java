package com.example.domain;


import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;

import java.sql.*;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private JdbcOperations jdbcTemplate;
    private RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User newUser = new User();
        newUser.setUsername(rs.getString("username"));
        newUser.setPassword(rs.getString("password"));
        newUser.setId(rs.getInt("id"));
        newUser.setLevel(Level.valueOf(rs.getInt("level")));
        newUser.setRecommend(rs.getInt("recommend"));
        newUser.setLogin(rs.getInt("login"));
        return newUser;
    };

    public UserDaoImpl(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void add(User user) throws DuplicateUsernameException {
        try {
            jdbcTemplate.update("INSERT INTO users (id,username,password,level,recommend,login) VALUES (?,?,?,?,?,?)"
                    , user.getId(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getLevel().getValue(),
                    user.getRecommend(),
                    user.getLogin()
            );
        } catch (DuplicateKeyException e) {
            throw new DuplicateUsernameException(e);
        }

    }

    public User get(String username) {
        User user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE username = ?", new Object[]{username}, userRowMapper);
        return user;
    }

    public List<User> getAll() {
        List<User> users = jdbcTemplate.query("SELECT * FROM users", userRowMapper);
        return users;
    }

    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM users");
    }

    public int getCount() {
        return jdbcTemplate.query("select count(*) from users", (ResultSet rs) -> {
            rs.next();
            return rs.getInt(1);
        });

    }

    @Override
    public void update(User user) {
        jdbcTemplate.update("UPDATE users set username=?, password=?, level=?, recommend=?, login=? where id=?",
                user.getUsername(),
                user.getPassword(),
                user.getLevel().getValue(),
                user.getRecommend(),
                user.getLogin(),
                user.getId()
        );
    }

//    public void jdbcContextWithStatement(StatementStrategy stmt) throws SQLException, ClassNotFoundException {
//        Connection c = null;
//        PreparedStatement ps = null;
//        try {
//            c = connectionCreator.getConnection();
//            ps = stmt.makePreparedStatement(c);
//            ps.executeUpdate();
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
//                } catch (SQLException e) {
//                    throw e;
//                }
//            }
//        }
//    }
}
