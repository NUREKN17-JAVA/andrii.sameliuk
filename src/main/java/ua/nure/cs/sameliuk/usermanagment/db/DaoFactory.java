package ua.nure.cs.sameliuk.usermanagment.db;

import java.io.IOException;
import java.util.Properties;

public class DaoFactory {

    private final Properties properties;

    public DaoFactory() {
        this.properties = new Properties();
        try {
            properties.load(getClass().getClassLoader()
                                      .getResourceAsStream("settings.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ConnectionFactory createConnection () {
        String user = properties.getProperty("connection.user");
        String password = properties.getProperty("connection.password");
        String url = properties.getProperty("connection.url");
        String driver = properties.getProperty("connection.driver");

        return new ConnectionFactoryImpl(user, password, url, driver);
    }

    public Dao getDao () {
        Dao result = null;
        return result;
    }
}
