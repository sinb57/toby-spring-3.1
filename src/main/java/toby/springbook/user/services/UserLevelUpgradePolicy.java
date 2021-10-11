package toby.springbook.user.services;

import toby.springbook.user.domain.User;

public interface UserLevelUpgradePolicy {

    boolean canUpgradeLevel(User user);
    void upgradeLevel(User user);
}
