package ua.nure.cs.sameliuk.usermanagment.db;

import java.io.IOException;
import java.util.Properties;

/**
 * An implementation of factory for data transfer objects.
 *
 * @implNote Implemented as Singleton.
 */
public abstract class DaoFactory {

    private static final String PROPERTIES = "settings.properties";
    protected static final String HSQLDB_USER_DAO = "dao.UserDao";
    private static final String DAO_FACTORY = "dao.factory";

    protected static Properties properties;

    private static DaoFactory instance;

    static {
        properties = new Properties();
        try {
            properties.load(DaoFactory.class
                                    .getClassLoader()
                                    .getResourceAsStream(PROPERTIES));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected DaoFactory() {
    }

    /**
     * Obtains an instance of {@code DaoFactory}.
     */
    public static synchronized DaoFactory getInstance() {
        if (instance == null) {
            try {
                Class factoryClass = Class.forName(properties.getProperty(DAO_FACTORY));
                instance = (DaoFactory) factoryClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    public static void init(Properties prop) {
        properties = prop;
        instance = null;
    }

    protected ConnectionFactory createConnection() {
        return new ConnectionFactoryImpl(properties);
    }

    /**
     * Obtains the DTO.
     */
    public abstract UserDao getDao() throws ReflectiveOperationException;
}
