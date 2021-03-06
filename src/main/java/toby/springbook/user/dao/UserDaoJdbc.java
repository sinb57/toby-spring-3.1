package toby.springbook.user.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import toby.springbook.user.domain.Level;
import toby.springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoJdbc implements UserDao {
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<User> userMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("password"),
                    Level.valueOf(rs.getInt("level")),
                    rs.getInt("login"),
                    rs.getInt("recommend")
            );
        }
    };

    public int add(final User user) {
        return jdbcTemplate.update("insert into users values (?,?,?,?,?,?)",
                user.getId(), user.getName(), user.getPassword(),
                user.getLevel().intValue(), user.getLogin(), user.getRecommend());
    }

    public User get(String id) {
        return jdbcTemplate.queryForObject("select  * from users where id = ?", userMapper, id);
    }

    public List<User> getAll() {
        return jdbcTemplate.query("select * from users", userMapper);
    }

    public int deleteAll() {
        return jdbcTemplate.update("delete from users");
    }

    public int getCount() {
        return jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }

    public int update(User user) {
        return jdbcTemplate.update("update users " +
                "set name = ?, password = ?, level = ?, login = ?, recommend = ? where id = ?",
                user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getId());
    }
}
