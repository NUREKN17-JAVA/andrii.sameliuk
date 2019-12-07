package ua.nure.cs.sameliuk.usermanagment.db;

import junit.framework.TestCase;

public class DaoFactoryTest extends TestCase {

    public void testUserDao() {
        try {
            DaoFactory daoFactory = DaoFactory.getInstance();
            assertNotNull("UserDao factory instance is null", daoFactory);
            UserDao userDao = daoFactory.getDao();
            assertNotNull("UserDao instance is null", userDao);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }
}
