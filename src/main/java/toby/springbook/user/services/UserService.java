package toby.springbook.user.services;

import toby.springbook.user.dao.UserDao;

public class UserService {

    UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
