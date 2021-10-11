package toby.springbook.user.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import toby.springbook.user.dao.UserDaoJdbc;
import toby.springbook.user.domain.Level;
import toby.springbook.user.domain.User;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations="/applicationContext.xml")
public class UserDaoTest {

    @Autowired
    private UserDaoJdbc dao;
    private User user1, user2, user3;

    @BeforeEach
    public void setUp() {
        this.user1 = new User("gyumee", "박성철", "springno1", Level.BASIC, 1, 0);
        this.user2 = new User("leegw700", "이길원", "springno2", Level.SILVER, 55, 10);
        this.user3 = new User("bumjin", "박범진", "springno3", Level.GOLD, 100, 40);

        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);
    }

    @Test
    public void addAndGet() {
        dao.add(user1);
        dao.add(user2);
        assertThat(dao.getCount()).isEqualTo(2);

        User userget1 = dao.get(user1.getId());
        checkSameUser(user1, userget1);

        User userget2 = dao.get(user2.getId());
        checkSameUser(user2, userget2);
    }

    @Test
    public void getAll() {
        User[] originList = {user1, user2, user3};
        List<User> dbList = null;

        for (int i=0; i<3; i++) {
            User originUser = originList[i];
            User dbUser = null;

            dao.add(originUser);
            dbList = dao.getAll();
            dbUser = dbList.get(i);

            assertThat(dbList.size()).isEqualTo(i+1);
        }
    }


    @Test
    public void getUserFailure() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            dao.get("unknown_id");
        });

    }

    @DisplayName("getCount 메소드 테스트")
    @Test
    public void count() {
        User[] userList = {user1, user2, user3};

        for (int i=0; i<3; i++) {
            User user = userList[i];
            dao.add(user);
            assertThat(dao.getCount()).isEqualTo(i+1);
        }
    }

    @Test
    public void duplicatedKey() {
        Assertions.assertThrows(DuplicateKeyException.class, () -> {
            dao.add(user1);
            dao.add(user1);
        });
    }

    @Test
    public void update() {
        int affectedRows = 0;
        dao.add(user1);

        user1.setName("오민규");
        user1.setPassword("springno6");
        user1.setLevel(Level.GOLD);
        user1.setLogin(100);
        user1.setRecommend(999);

        affectedRows = dao.update(user1);
        assertThat(affectedRows).isEqualTo(1);

        User user1update = dao.get(user1.getId());
        checkSameUser(user1, user1update);
    }

    private void checkSameUser(User originUser, User dbUser) {
        assertThat(dbUser.getId()).isEqualTo(originUser.getId());
        assertThat(dbUser.getName()).isEqualTo(originUser.getName());
        assertThat(dbUser.getPassword()).isEqualTo(originUser.getPassword());
        assertThat(dbUser.getLevel()).isEqualTo(originUser.getLevel());
        assertThat(dbUser.getLogin()).isEqualTo(originUser.getLogin());
        assertThat(dbUser.getRecommend()).isEqualTo(originUser.getRecommend());
    }
}
