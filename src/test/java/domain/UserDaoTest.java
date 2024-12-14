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


        User newUser = new User();
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao userDao = context.getBean("userDao", UserDao.class);
        CountingConnectionCreatorDecorator connectionCreator = context.getBean("connectionCreator", CountingConnectionCreatorDecorator.class);
        System.out.println(connectionCreator.getCount());

        userDao.deleteAll();
        assert userDao.getCount()==0 : "deleteAll failed";

        newUser.setPassword("password");
        newUser.setUsername("username");
        userDao.add(newUser);

        User user = userDao.get(newUser.getUsername());

        assert userDao.getCount()==1 : "add failed";
        assert user.getUsername().equals(newUser.getUsername());
        assert user.getPassword().equals(newUser.getPassword());




    }
}
