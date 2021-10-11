package toby.springbook.user.services;

import toby.springbook.user.dao.UserDao;
import toby.springbook.user.domain.Level;
import toby.springbook.user.domain.User;

import java.util.List;

public class UserService {

    UserLevelUpgradePolicy userLevelUpgradePolicy;
    UserDao userDao;

    public void setUserLevelUpgradePolicy(UserLevelUpgradePolicy policy) {
        userLevelUpgradePolicy = policy;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void upgradeLevels() {
        List<User> users = userDao.getAll();
        for (User user : users) {
            if (userLevelUpgradePolicy.canUpgradeLevel(user)) {
                this.upgradeLevel(user);
            }
        }
    }

    private void upgradeLevel(User user) {
        userLevelUpgradePolicy.upgradeLevel(user);
        userDao.update(user);
    }


    public void add(User user) {
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }
}
