package com.example.service;

import com.example.domain.Level;
import com.example.domain.User;
import com.example.domain.UserDao;

import com.example.service.upgrade.UpgradePolicy;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.List;

public class UserServiceImpl implements UserService {
    UserDao userDao;
    UpgradePolicy upgradePolicy;
    MailSender mailSender;


    public UserServiceImpl(UserDao userDao, UpgradePolicy upgradePolicy, MailSender mailSender) {
        this.userDao = userDao;
        this.upgradePolicy = upgradePolicy;
        this.mailSender = mailSender;
    }

    public UserServiceImpl() {
    }


    public void upgradeLevels() throws RuntimeException {
        List<User> users = userDao.getAll();
        for (User user : users) {
            if (upgradePolicy.isUpgradable(user))
                upgradeLevel(user);
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
