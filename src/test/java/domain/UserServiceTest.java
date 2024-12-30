package domain;

import com.example.domain.DaoFactory;
import com.example.domain.Level;
import com.example.domain.User;
import com.example.domain.UserDao;
import com.example.service.UserService;
import com.example.service.UsualUpgradePolicy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;


import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserServiceTest {
    UserService userService;
    static ApplicationContext context;
    List<User> users = new ArrayList<>();

    PlatformTransactionManager transactionManager;
    UserDao userDao;
    DataSource dataSource ;

    static class TestUserService extends UserService {
        private int id;
        public TestUserService (int id){
            this.id = id;
        }
        @Override
        protected void upgradeLevel(User user){
            if(user.getId() == this.id) throw new UpgradeException();
            super.upgradeLevel(user);
        }
    }
    static class UpgradeException extends RuntimeException{
    }
    @BeforeAll
    public static void setUpContext() {
        context = new AnnotationConfigApplicationContext(DaoFactory.class);
    }

    @BeforeEach
    void setUp() {
        userService = context.getBean(UserService.class);
        userDao = context.getBean(UserDao.class);
        dataSource = context.getBean(DataSource.class);
        transactionManager = context.getBean(PlatformTransactionManager.class);
        users = Arrays.asList(
                new User(1, "username1", "password1", Level.BASIC, 49, 0),
                new User(2, "username2", "password2", Level.BASIC, 50, 0),
                new User(3, "username3", "password2", Level.SILVER, 50, 20),
                new User(4, "username4", "password2", Level.SILVER, 50, 30),
                new User(5, "username5", "password3", Level.GOLD, 20, 30)
        );

    }

    @Test
    void isthesameInstace() {
        System.out.println(this);
        System.out.println(userService);
        System.out.println(context);
    }

    @Test
    void isthesameInstace2() {
        System.out.println(this);
        System.out.println(userService);
        System.out.println(context);

    }

    @Test
    void upgradeLevels() throws Exception {
        userDao.deleteAll();
        for ( User user : users ) {
            userDao.add(user);
        }
        userService.upgradeLevels();
        checkLevel(users.get(0),false);
        checkLevel(users.get(1),true);
        checkLevel(users.get(2),false);
        checkLevel(users.get(3),true);
        checkLevel(users.get(4),false);

    }
    @Test
    void upgradeAllOrNothing() throws  Exception {
        //Arrange
        UserService testUserService = new TestUserService(users.get(3).getId());
        testUserService.setUserDao(this.userDao);
        testUserService.setUpgradePolicy(new UsualUpgradePolicy());
        testUserService.setTransactionManager(this.transactionManager);

        userDao.deleteAll();
        for(User user : users){
            userDao.add(user);
        }

        try {
            testUserService.upgradeLevels();//ACT
            throw new RuntimeException("No Exception thrown");
        } catch(UpgradeException e) {
            System.out.println("Upgrade Exception !");
        }
        checkLevel(users.get(0),false);
        checkLevel(users.get(1),false);
        checkLevel(users.get(2),false);


    }
    private void checkLevel(User user, boolean upgrade) {
        User updatedUser = userDao.get(user.getUsername());
        if(upgrade){
            assert updatedUser.getLevel() ==user.getLevel().nextLevel();
        } else{
            assert updatedUser.getLevel() ==user.getLevel();
        }

    }


    @Test
    void sendMail(){
        userService.sendEmail();
    }


    @Test void add(){
        userDao.deleteAll();
        User userWithoutLevel = users.get(0);
        User userWithLevel = users.get(4);
        userWithoutLevel.setLevel(null);
        userService.add(userWithoutLevel);
        userService.add(userWithLevel);
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getUsername());
        User userWithLevelRead =  userDao.get(userWithLevel.getUsername());
        assert userWithoutLevelRead.getLevel() == Level.BASIC;
        assert userWithLevel.getLevel() == userWithLevelRead.getLevel();
    }
}
