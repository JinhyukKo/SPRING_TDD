package domain;

import com.example.domain.CountingConnectionCreatorDecorator;
import com.example.domain.DaoFactory;
import com.example.domain.User;
import com.example.domain.UserDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


public class UserDaoTest {
    UserDao userDao;
    static ApplicationContext context;
    User newUser1;
    User newUser2;
    User newUser3;

    @BeforeAll
    public static void setUpContext() {
        context = new AnnotationConfigApplicationContext(DaoFactory.class);
    }

    @BeforeEach
    public void setUp() throws SQLException {
        this.userDao = context.getBean("userDao", UserDao.class);
        this.newUser1 = new User("username1", "password1");
        this.newUser2 = new User("username2", "password2");
        this.newUser3 = new User("username3", "password3");

    }

    @Test
    public void addAndGet() throws ClassNotFoundException, SQLException {
        //Act
        userDao.deleteAll();

        assertEquals(userDao.getCount(), 0);
        //Assert
        assert userDao.getCount() == 0 : "deleteAll failed";

        //Act
        userDao.add(newUser1);
        userDao.add(newUser2);

        //Assert
        assert userDao.getCount() == 2 : "add failed";

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
    public void count() throws ClassNotFoundException, SQLException {
        userDao.deleteAll();

        assert userDao.getCount() == 0 : "deleteAll failed";
        userDao.add(newUser1);
        assert userDao.getCount() == 1 : "add failed";
        userDao.add(newUser2);
        assert userDao.getCount() == 2 : "add failed";
        userDao.add(newUser3);
        assert userDao.getCount() == 3 : "add failed";
    }

    @Test
    public void getUserFail() throws ClassNotFoundException, SQLException {
        userDao.deleteAll();
        assert userDao.getCount() == 0 : "deleteAll failed";

        assertThrows(SQLException.class, () -> userDao.get("no such user"));

    }

    @Test
    public void deleteAll() throws ClassNotFoundException, SQLException {
        //Act
        userDao.deleteAll();

        assertEquals(userDao.getCount(), 0);
        //Assert
        assert userDao.getCount() == 0 : "deleteAll failed";

    }

}
