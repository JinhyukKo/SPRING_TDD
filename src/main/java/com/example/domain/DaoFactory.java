package com.example.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;

@Configuration
public class DaoFactory {

    @Bean
    public UserDao userDao() {
        UserDao userDao = new UserDao(jdbcTemplate());
        return userDao;
    }
    @Bean
    public ConnectionCreator connectionCreator() {
        return new CountingConnectionCreatorDecorator(realConnectionCreator());
    }
    @Bean
    public ConnectionCreator realConnectionCreator (){
        return new ConnectionCreatorImpl();
    }
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/tobi");
        dataSource.setUsername("root");
        dataSource.setPassword("password");
        return dataSource;

    }
    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }
}
