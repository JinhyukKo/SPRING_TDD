package com.example.service;

import com.example.domain.Level;
import com.example.domain.User;
import com.example.domain.UserDao;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserService {
    UserDao userDao;
    UpgradePolicy upgradePolicy;
    DataSource dataSource;

    public UserService(UserDao userDao, UpgradePolicy upgradePolicy) {
        this.userDao = userDao;
        this.upgradePolicy = upgradePolicy;
    }
    public UserService() {
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void upgradeLevels() throws Exception {
        TransactionSynchronizationManager.initSynchronization();
        Connection c = DataSourceUtils.getConnection(dataSource);
        try{
            c.setAutoCommit(false);
            List<User> users = userDao.getAll();
            for (User user : users) {
                if (upgradePolicy.isUpgradable(user))
                    upgradeLevel(user);
            }
            c.commit();
        } catch (SQLException e) {
            try {
                c.rollback();
                throw e;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } finally {
            DataSourceUtils.releaseConnection(c, dataSource);
            TransactionSynchronizationManager.unbindResource(dataSource);
            TransactionSynchronizationManager.clearSynchronization();
        }

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
