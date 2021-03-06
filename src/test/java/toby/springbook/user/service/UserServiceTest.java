package toby.springbook.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import toby.springbook.user.dao.UserDao;
import toby.springbook.user.domain.Level;
import toby.springbook.user.domain.User;
import toby.springbook.user.services.UserService;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static toby.springbook.user.services.UsualUserLevelUpgradePolicy.MIN_LOGCOUNT_FOR_SILVER;
import static toby.springbook.user.services.UsualUserLevelUpgradePolicy.MIN_RECOMMEND_FOR_GOLD;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations="/applicationContext.xml")
public class UserServiceTest {

    @Autowired UserService userService;
    @Autowired UserDao userDao;
    List<User> users;

    @BeforeEach
    public void setUp() {
        users = Arrays.asList(
                new User("bumjin", "박범진", "p1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER-1, 0),
                new User("joytouch", "강명성", "p2", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0),
                new User("erwins", "신승한", "p3", Level.SILVER, MIN_RECOMMEND_FOR_GOLD-1, 29),
                new User("madnite1", "이상호", "p4", Level.SILVER, MIN_RECOMMEND_FOR_GOLD, 30),
                new User("green", "오민규", "p5", Level.GOLD, 100, Integer.MAX_VALUE)
        );

        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);
    }

    @Test
    public void upgradeLevels() {
        for(User user : users) {
            userDao.add(user);
        }

        userService.upgradeLevels();

        checkLevel(users.get(0), false);
        checkLevel(users.get(1), true);
        checkLevel(users.get(2), false);
        checkLevel(users.get(3), true);
        checkLevel(users.get(4), false);
    }

    private void checkLevel(User user, boolean upgraded) {
        User userUpdate = userDao.get(user.getId());
        if (upgraded) {
            assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel().nextLevel());
        }
        else {
            assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel());
        }
    }

    @Test
    public void add() {
        User userWithLevel = users.get(4);
        User userWithoutLevel = users.get(0);
        userWithoutLevel.setLevel(null);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        assertThat(userWithLevelRead.getLevel()).isEqualTo(userWithLevel.getLevel());
        assertThat(userWithoutLevelRead.getLevel()).isEqualTo(Level.BASIC);
    }

}
