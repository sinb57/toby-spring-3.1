package toby.springbook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import toby.springbook.user.domain.Level;
import toby.springbook.user.domain.User;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTest {

    User user;

    @BeforeEach
    public void setUp() {
        user = new User();
    }

    @Test
    public void upgradeLevel() {
        Level[] levels = Level.values();
        for (Level level : levels) {
            if (level.nextLevel() == null) continue;
            user.setLevel(level);
            user.upgradeLevel();
            assertThat(user.getLevel()).isEqualTo(level.nextLevel());
        }
    }

    @Test
    public void cannotUpgradeLevel() {
        Level[] levels = Level.values();
        for (Level level : levels) {
            if (level.nextLevel() != null) continue;
            assertThrows(IllegalStateException.class, () -> {
                user.setLevel(level);
                user.upgradeLevel();
            });
        }
    }

}
