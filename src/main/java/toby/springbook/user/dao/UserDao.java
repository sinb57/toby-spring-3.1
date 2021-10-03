package toby.springbook.user.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import toby.springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource; // 아직 JdbcContext를 적용하지 않은 메소드를 위해 남겨둔다.

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
    }

    public void add(final User user) {
        jdbcTemplate.update("insert into users values (?,?,?)", user.getId(), user.getName(), user.getPassword());
    }

    public User get(String id) {
        return jdbcTemplate.queryForObject("select  * from users where id = ?",
                new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                        User user = new User();
                        user.setId(rs.getString("id"));
                        user.setName(rs.getString("name"));
                        user.setPassword(rs.getString("password"));
                        return user;
                    }
                }, id);
    }

    public void deleteAll() {
        jdbcTemplate.update("delete from users");
    }


    public int getCount() {
        return jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }

}
