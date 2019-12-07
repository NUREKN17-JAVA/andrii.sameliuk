package ua.nure.cs.sameliuk.usermanagment.db;

public class DaoFactoryImpl extends DaoFactory {

    @Override
    public UserDao getDao() throws ReflectiveOperationException {
        UserDao result = null;
        try {
            Class hsqldbUserDaoClass = Class.forName(properties.getProperty(HSQLDB_USER_DAO));
            result = (UserDao) hsqldbUserDaoClass.newInstance();
            result.setConnectionFactory(createConnection());
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new ReflectiveOperationException(e);
        }
        return result;
    }
}
