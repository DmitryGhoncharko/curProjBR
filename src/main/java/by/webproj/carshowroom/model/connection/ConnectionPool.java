package by.webproj.carshowroom.model.connection;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool {
    /**
     *
     * @return connection from pool
     * @throws SQLException
     */
    Connection getConnection() throws SQLException;
}