package domain;

import com.example.domain.Level;
import com.example.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    User user;

    @BeforeEach
    void setup(){
        user = new User();
    }
    @Test
    public void upgradeLevel(){
        Level[] levels = Level.values();

        for(Level level : levels){
            if(level.nextLevel()==null) continue;
            user.setLevel(level);
            user.upgradeLevel();
            assert user.getLevel() == level.nextLevel();
        }
    }

    @Test
    public void upgradeLevelException(){
        Level[] levels = Level.values();
        for(Level level : levels){
            if(level.nextLevel()!=null) continue;
            user.setLevel(level);
            assertThrows(IllegalArgumentException.class, user::upgradeLevel);
        }
    }
}
