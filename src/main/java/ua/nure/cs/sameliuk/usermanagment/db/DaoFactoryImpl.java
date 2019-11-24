package ua.nure.cs.sameliuk.usermanagment.db;

public class DaoFactoryImpl extends DaoFactory {

    @Override
    public Dao getDao() throws ReflectiveOperationException {
        Dao result = null;
        try {
            Class hsqldbUserDaoClass = Class.forName(properties.getProperty(HSQLDB_USER_DAO));
            result = (Dao) hsqldbUserDaoClass.newInstance();
            result.setConnectionFactory(createConnection());
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new ReflectiveOperationException(e);
        }
        return result;
    }
}
