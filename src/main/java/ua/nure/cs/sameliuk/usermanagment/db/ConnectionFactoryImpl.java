package ua.nure.cs.sameliuk.usermanagment.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * An implementation of {@link ConnectionFactory}.
 */
public class ConnectionFactoryImpl implements ConnectionFactory {

    private final String user;
    private final String password;
    private final String url;
    private final String driver;

    ConnectionFactoryImpl(String user, String password, String url, String driver) {
        this.user = user;
        this.password = password;
        this.url = url;
        this.driver = driver;
    }

    /**
     * {@inheritDoc}
     *
     * @throws DataBaseException
     *         if during the attempt to get connection to database something goes wrong
     */
    @Override
    public Connection createConnection() throws DataBaseException {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }
}
