package toby.springbook.user.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import toby.springbook.user.domain.User;

import javax.sql.DataSource;
import java.util.List;

public class UserDao {

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(final User user) {
        jdbcTemplate.update("insert into users values (?,?,?)", user.getId(), user.getName(), user.getPassword());
    }

    public User get(String id) {
        return jdbcTemplate.queryForObject("select  * from users where id = ?",
                (rs, nowNum) -> new User(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("password")
                ), id);
    }

    public List<User> getAll() {
        return jdbcTemplate.query("select * from users",
                (rs, nowNum) -> new User(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("password")
                ));
    }

    public void deleteAll() {
        jdbcTemplate.update("delete from users");
    }


    public int getCount() {
        return jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }

}
