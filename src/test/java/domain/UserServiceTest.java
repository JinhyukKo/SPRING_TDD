package domain;

import com.example.domain.DaoFactory;
import com.example.domain.Level;
import com.example.domain.User;
import com.example.domain.UserDao;
import com.example.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserServiceTest {
    UserService userService;
    static ApplicationContext context;
    List<User> users = new ArrayList<>();
    UserDao userDao;

    @BeforeAll
    public static void setUpContext() {
        context = new AnnotationConfigApplicationContext(DaoFactory.class);
    }

    @BeforeEach
    void setUp() {
        userService = context.getBean(UserService.class);
        userDao = context.getBean(UserDao.class);
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
    void upgradeLevels() {
        userDao.deleteAll();
        for ( User user : users ) {
            userDao.add(user);
        }
        userService.upgradeLevels();

        checkLevel(users.get(0),Level.BASIC);
        checkLevel(users.get(1),Level.SILVER);
        checkLevel(users.get(2),Level.SILVER);
        checkLevel(users.get(3),Level.GOLD);
        checkLevel(users.get(4),Level.GOLD);



    }
    private void checkLevel(User user, Level level) {
        User updatedUser = userDao.get(user.getUsername());
        assert updatedUser.getLevel() == level;
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
