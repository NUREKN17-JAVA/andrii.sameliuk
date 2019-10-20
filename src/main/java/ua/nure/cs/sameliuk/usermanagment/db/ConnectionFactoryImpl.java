package ua.nure.cs.sameliuk.usermanagment.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactoryImpl implements ConnectionFactory {

    private String url = "jdbc:hsqldb:file:/db/usermanagment";
    private String user = "sa";
    private String password = "";
    private String driver = "org.hsqldb.jdbcDrive";

    @Override
    public Connection createConnection() throws DataBaseException {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException();
        }
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }
}
