package toby.springbook;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import toby.springbook.user.dao.UserDao;
import toby.springbook.user.domain.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations="/applicationContext.xml")
public class UserDaoTest {

    @Autowired
    private ApplicationContext context;
    private UserDao dao;

    @BeforeEach
    public void setUp() {
        dao = context.getBean("userDao", UserDao.class);
    }

    @Test
    public void addAndGet() {
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
    public void getAll() {
        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        List<User> userListZero = dao.getAll();
        assertThat(userListZero.size()).isEqualTo(0);

        List<User> originList = new ArrayList<>();
        for (int i=1; i<6; i++) {
            User user = createUser(i);
            originList.add(user);
            dao.add(user);

            List<User> dbList = dao.getAll();
            assertThat(dbList.size()).isEqualTo(originList.size());

            checkSameUser(originList, dbList);
        }

    }
    
    private void checkSameUser(List<User> originList, List<User> dbList) {
        for (int i=0; i<originList.size(); i++) {
            User user1 = originList.get(i);
            User user2 = dbList.get(i);
            assertThat(user1.getId()).isEqualTo(user2.getId());
            assertThat(user1.getName()).isEqualTo(user2.getName());
            assertThat(user1.getPassword()).isEqualTo(user2.getPassword());
        }
    }


    @Test
    public void getUserFailure() {
        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            dao.get("unknown_id");
        });

    }



    @DisplayName("getCount 메소드 테스트")
    @Test
    public void count() {
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
