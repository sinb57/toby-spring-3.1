package toby.springbook.user.dao;

import toby.springbook.user.domain.User;

import java.util.List;

public interface UserDao {

    int add(User user);
    User get(String id);
    List<User> getAll();
    int deleteAll();
    int getCount();
    int update(User user);

}
