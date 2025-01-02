package com.example.domain;

import com.example.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.MailSender;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DaoFactory {

    @Bean
    public UserServiceImpl userService() {
        return new UserServiceImpl(userDao(),upgradePolicy(),transactionManager(),mailSender());
    }
    @Bean
    public MailSender mailSender(){
        return new GmailMailSender();
    }
    @Bean
    public UpgradePolicy upgradePolicy() {
        return new UsualUpgradePolicy();
    }
    @Bean
    PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
    @Bean
    public UserDao userDao() {
        UserDao userDao = new UserDaoImpl(jdbcTemplate());
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
