package toby.springbook;

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
import toby.springbook.user.domain.User;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations="/applicationContext.xml")
public class UserDaoJdbcTest {

    @Autowired
    private UserDaoJdbc dao;
    private User user1, user2, user3;

    @BeforeEach
    public void setUp() {
        this.user1 = new User("gyumee", "박성철", "springno1");
        this.user2 = new User("leegw700", "이길원", "springno2");
        this.user3 = new User("bumjin", "박범진", "springno3");

        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);
    }

    @Test
    public void addAndGet() {
        dao.add(user1);
        dao.add(user2);
        assertThat(dao.getCount()).isEqualTo(2);

        User userget1 = dao.get(user1.getId());
        assertThat(userget1.getName()).isEqualTo(user1.getName());
        assertThat(userget1.getPassword()).isEqualTo(user1.getPassword());

        User userget2 = dao.get(user2.getId());
        assertThat(userget2.getName()).isEqualTo(user2.getName());
        assertThat(userget2.getPassword()).isEqualTo(user2.getPassword());
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

}
