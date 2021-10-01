package toby.springbook;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import toby.springbook.user.dao.UserDao;
import toby.springbook.user.domain.User;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;

public class UserDaoTest {
    private UserDao dao;

    @BeforeEach
    public void setUp() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        dao = context.getBean("userDao", UserDao.class);
    }

    @Test
    public void addAndGet() throws SQLException {

        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        User user1 = createUser(1);
        dao.add(user1);
        User user2 = createUser(2);
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
    public void getUserFailure() throws SQLException {

        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            dao.get("unknown_id");
        });

    }



    @DisplayName("getCount 메소드 테스트")
    @Test
    public void count() throws SQLException {

        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        for (int i=1; i<6; i++) {
            User user = createUser(i);
            dao.add(user);
            assertThat(dao.getCount()).isEqualTo(i);
        }
    }

    private User createUser(int i) {
        String id = "id" + i;
        String name = "name" + i;
        String password = "password" + i;
        return new User(id, name, password);
    }
}
