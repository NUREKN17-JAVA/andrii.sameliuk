package ua.nure.cs.sameliuk.usermanagment.db;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import ua.nure.cs.sameliuk.usermanagment.domain.User;

import java.util.Collection;
import java.util.Date;

public class HsqldbUserDaoTest extends DatabaseTestCase {

    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private HsqldbUserDao hsqldbUserDao;
    private ConnectionFactory connectionFactory;

    public void testCreate() {
        try {
            User user = getUser();
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

    private User getUser() {
        User user = new User();
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setDateOfBirth(new Date());
        return user;
    }

    public void testFindAll() {
        try {
            User user = getUser();
            int expectedCollectionSize = hsqldbUserDao.findAll()
                                                      .size() + 1;
            hsqldbUserDao.create(user);
            Collection users = hsqldbUserDao.findAll();
            assertNotNull("Collection is null", users);
            assertEquals("Collection size.", expectedCollectionSize, users.size());
        } catch (DataBaseException e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    @Override
    protected void setUp() throws Exception {
        connectionFactory = new ConnectionFactoryImpl("sa", "",
                                                      "jdbc:hsqldb:file:db/usermanagment",
                                                      "org.hsqldb.jdbcDriver");
        hsqldbUserDao = new HsqldbUserDao(connectionFactory);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        connectionFactory = new ConnectionFactoryImpl("sa", "",
                                                      "jdbc:hsqldb:file:db/usermanagment",
                                                      "org.hsqldb.jdbcDriver");
        return new DatabaseConnection(connectionFactory.createConnection());
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        IDataSet dataSet = new XmlDataSet(getClass().getClassLoader()
                                                    .getResourceAsStream("usersDataSet.xml"));
        return dataSet;
    }
}
