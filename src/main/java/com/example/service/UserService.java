package com.example.service;

import com.example.domain.Level;
import com.example.domain.User;
import com.example.domain.UserDao;
import jakarta.mail.internet.MimeMessage;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.transaction.PlatformTransactionManager;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;
import java.util.Properties;

public class UserService {
    UserDao userDao;
    UpgradePolicy upgradePolicy;
    PlatformTransactionManager transactionManager;
    MailSender mailSender;




    public UserService(UserDao userDao, UpgradePolicy upgradePolicy, PlatformTransactionManager transactionManager,MailSender mailSender) {
        this.userDao = userDao;
        this.upgradePolicy = upgradePolicy;
        this.transactionManager = transactionManager;
        this.mailSender = mailSender;
    }

    public UserService() {
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void upgradeLevels() throws RuntimeException {
        TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            List<User> users = userDao.getAll();
            for (User user : users) {
                if (upgradePolicy.isUpgradable(user))
                    upgradeLevel(user);
            }
            transactionManager.commit(status);
        } catch (RuntimeException e) {
            transactionManager.rollback(status);
            throw e;
        }
    }


    public void sendUpgradeEmail(User user) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("kobin1970@gmail.com");
        mailMessage.setSubject("사용자님의 등급이 " + user.getLevel().name() + "로 업그레이드 됐습니다.");
        mailMessage.setText("축하드립니다.");

        mailSender.send(mailMessage);


    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    protected void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
        sendUpgradeEmail(user);
    }

    public void setUpgradePolicy(UpgradePolicy upgradePolicy) {
        this.upgradePolicy = upgradePolicy;
    }

    public void add(User user) {
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }
    public MailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

}
