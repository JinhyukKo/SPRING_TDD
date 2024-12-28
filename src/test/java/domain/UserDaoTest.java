package domain;

import com.example.domain.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

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
        this.newUser1 = new User(1, "username1", "password1", Level.BASIC, 1,0);
        this.newUser2 = new User(2, "username2", "password2", Level.BASIC, 2,0);
        this.newUser3 = new User(3, "username3", "password3" , Level.GOLD, 10,0);
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
        assert user.equals(newUser1) : "get failed";
        assert user2.equals(newUser2) : "get failed";
    }
    @Test void userEquals(){
        assert newUser1.equals(newUser1);
        assert newUser2.equals(newUser2);
        assert !newUser1.equals(newUser3);
    }

    @Test void update(){
        userDao.deleteAll();
        userDao.add(newUser1);
        userDao.add(newUser2);
        assert userDao.getCount() == 2 : "update failed";
        newUser1.setUsername("edited username");
        newUser1.setPassword("edited password");
        newUser1.setLevel(Level.GOLD);
        userDao.update(newUser1);
        User updatedUser = userDao.get(newUser1.getUsername());
        User nonUpatedUser = userDao.get(newUser2.getUsername());
        assert updatedUser.equals(newUser1) : "update failed";
        assert nonUpatedUser.equals(newUser2) : "update failed";

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

        assertThrows(DataAccessException.class, () -> userDao.get("no such user"));

    }
    @Test
    public void addAndGetAll() throws ClassNotFoundException, SQLException {
        userDao.deleteAll();

        List<User> users = null;
        assert userDao.getCount() == 0 : "deleteAll failed";

        users = userDao.getAll();
        assert users.size() == 0 : "getAll failed";

        userDao.add(newUser1);
        users = userDao.getAll();
        assert users.size() == 1 : "get failed";
        assert users.get(0).equals(newUser1) : "get failed";

        userDao.add(newUser2);
        users= userDao.getAll();
        assert users.size() == 2 : "get failed";
        assert users.get(0).equals(newUser1) : "get failed";
        assert users.get(1).equals(newUser2) : "get failed";
        userDao.add(newUser3);
        users = userDao.getAll();
        assert users.size() == 3 : "get failed";
        assert users.get(0).equals(newUser1) : "get failed";
        assert users.get(1).equals(newUser2) : "get failed";
        assert users.get(2).equals(newUser3) : "get failed";
    }

    @Test
    public void deleteAll() throws ClassNotFoundException, SQLException {
        //Act
        userDao.deleteAll();

        assertEquals(userDao.getCount(), 0);
        //Assert
        assert userDao.getCount() == 0 : "deleteAll failed";

    }
    @Test
    public void addDuplicate() {
        userDao.deleteAll();
        userDao.add(newUser1);
        assertThrows(DuplicateUsernameException.class, () -> userDao.add(newUser1));
    }




}
