package ua.nure.cs.sameliuk.usermanagment.db;

import junit.framework.TestCase;

public class DaoFactoryTest extends TestCase {

    public void testUserDao() {
        try {
            DaoFactory daoFactory = DaoFactory.getInstance();
            assertNotNull("Dao factory instance is null", daoFactory);
            Dao dao = daoFactory.getDao();
            assertNotNull("UserDao instance is null", dao);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }
}
