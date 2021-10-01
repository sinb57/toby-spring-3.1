package toby.springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementStrategy {

    PreparedStatement makePremparedStatement(Connection c) throws SQLException;
}
