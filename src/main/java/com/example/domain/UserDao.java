package com.example.domain;


import java.sql.*;

public class UserDao {
    private ConnectionCreator connectionCreator;

    public UserDao(ConnectionCreator connectionCreator) {
        this.connectionCreator = connectionCreator;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        StatementStrategy stmt = new AddStatement(user);
        jdbcContextWithStatement(stmt);
    }

    public User get(String username) throws ClassNotFoundException, SQLException {
        Connection c = connectionCreator.getConnection();
        PreparedStatement ps = c.prepareStatement("select * from users where username = ?");
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        User user = null;
        if (rs.next()) {
            user = new User();
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
        }
        rs.close();
        ps.close();
        c.close();
        if (user == null) {
            throw new SQLException("User not found");
        }
        return user;
    }

    public void deleteAll() throws ClassNotFoundException, SQLException {
        StatementStrategy stmt = new DeleteAllStatement();
        jdbcContextWithStatement(new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {
                return connection.prepareStatement("DELETE FROM users");
            }
        });
    }

    public int getCount() throws ClassNotFoundException, SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            c = connectionCreator.getConnection();
            ps = c.prepareStatement("select count(*) from users");
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
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
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
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
