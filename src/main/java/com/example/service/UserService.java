package com.example.service;

import com.example.domain.Level;
import com.example.domain.User;
import com.example.domain.UserDao;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class UserService {
    UserDao userDao;
    UpgradePolicy upgradePolicy;
    PlatformTransactionManager transactionManager;

    public UserService(UserDao userDao, UpgradePolicy upgradePolicy, PlatformTransactionManager transactionManager) {
        this.userDao = userDao;
        this.upgradePolicy = upgradePolicy;
        this.transactionManager = transactionManager;
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


    public void sendEmail(){
//        Properties props = new Properties();
//        props.put("mail.smtp.host", "smtp.gmail.com"); // Gmail SMTP 서버
//        props.put("mail.smtp.port", "587");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
//
//
//
//        // Session 객체 생성
//        Session session = Session.getDefaultInstance(props, new Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("kobin1970@gmail.com", "bzqf gpjh hkbf osvj");
//            }
//        });
//
//        try {
//            // 메일 생성
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress("kobin1970@gmail.com"));
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("kobin1970@gmail.com"));
//            message.setSubject("테스트 메일");
//            message.setText("JavaMail을 이용한 메일 발송 테스트입니다.");
//
//            // 메일 전송
//            Transport.send(message);
//            System.out.println("메일 전송 완료");
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }

        JavaMailSender
    }
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    protected void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
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
}
