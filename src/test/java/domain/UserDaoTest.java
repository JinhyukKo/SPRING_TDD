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
        //Arragne
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao userDao = context.getBean("userDao", UserDao.class);
        CountingConnectionCreatorDecorator connectionCreator = context.getBean("connectionCreator", CountingConnectionCreatorDecorator.class);
        User newUser1 = new User("username1","password1");
        User newUser2 = new User("username2","password2");

        //Assert
        userDao.deleteAll();

        //Act
        assert userDao.getCount()==0 : "deleteAll failed";

        //Act
        userDao.add(newUser1);
        userDao.add(newUser2);

        //Assert
        assert userDao.getCount()==2 : "add failed";

        //Act
        User user = userDao.get(newUser1.getUsername());
        User user2 = userDao.get(newUser2.getUsername());

        //Assert
        assert user.getUsername().equals(newUser1.getUsername()) : "get failed";
        assert user.getPassword().equals(newUser1.getPassword()) : "get failed";
        assert user2.getUsername().equals(newUser2.getUsername()) : "get failed";
        assert user2.getPassword().equals(newUser2.getPassword()) : "get failed";
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
