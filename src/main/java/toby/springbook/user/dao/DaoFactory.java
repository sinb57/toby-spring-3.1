package toby.springbook.user.dao;

public class DaoFactory {

    public ConnectionMaker connectionMaker() {
        return new DConnectionMaker();
    }

    public UserDao userDao() {
        return new UserDao(connectionMaker());
    }
}
