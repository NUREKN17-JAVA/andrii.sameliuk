package ua.nure.cs.sameliuk.usermanagment.db;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import ua.nure.cs.sameliuk.usermanagment.domain.User;

import java.util.Date;

public class HsqldbUserDaoTest extends DatabaseTestCase {

    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private HsqldbUserDao hsqldbUserDao;
    private ConnectionFactory connectionFactory;

    public void testCreate() {
        try {
            User user = new User();
            user.setFirstName(FIRST_NAME);
            user.setLastName(LAST_NAME);
            user.setDateOfBirth(new Date());
            assertNull(user.getId());

            User userToCheck = hsqldbUserDao.create(user);

            assertNotNull(userToCheck);
            assertNotNull(userToCheck.getId());
            assertEquals("Creating of user was failed.", user.getFirstName(),
                         userToCheck.getFirstName());
        } catch (DataBaseException e) {
            fail(e.toString());
        }
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        connectionFactory = new ConnectionFactoryImpl();
        hsqldbUserDao = new HsqldbUserDao(connectionFactory);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        connectionFactory = new ConnectionFactoryImpl();
        return new DatabaseConnection(connectionFactory.createConnection());
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return null;
    }
}
