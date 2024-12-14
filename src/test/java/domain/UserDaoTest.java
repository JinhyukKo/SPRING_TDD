package domain;

import com.example.domain.CountingConnectionCreatorDecorator;
import com.example.domain.DaoFactory;
import com.example.domain.User;
import com.example.domain.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class UserDaoTest {
    @Test
    public void addAndGet()  throws ClassNotFoundException, SQLException {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao userDao = context.getBean("userDao", UserDao.class);
        CountingConnectionCreatorDecorator connectionCreator = context.getBean("connectionCreator", CountingConnectionCreatorDecorator.class);
        userDao.deleteAll();
        assert userDao.getCount()==0 : "deleteAll failed";
        User newUser = new User();
        newUser.setPassword("password");
        newUser.setUsername("username");
        userDao.add(newUser);
        User user = userDao.get(newUser.getUsername());
        assert userDao.getCount()==1 : "add failed";
        assert user.getUsername().equals(newUser.getUsername());
        assert user.getPassword().equals(newUser.getPassword());
    }
    @Test
    public void count()  throws ClassNotFoundException, SQLException {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao userDao = context.getBean("userDao", UserDao.class);
        User user = new User("username1", "password1");
        User user2 = new User("username2", "password2");
        User user3 = new User("username3", "password3");

        userDao.deleteAll();
        assert userDao.getCount()==0 : "deleteAll failed";
        userDao.add(user);
        assert userDao.getCount()==1 : "add failed";
        userDao.add(user2);
        assert userDao.getCount()==2 : "add failed";
        userDao.add(user3);
        assert userDao.getCount()==3 : "add failed";

    }
}
