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
    private static final long ID = 1L;
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:hsqldb:file:db/usermanagment";
    private static final String DRIVER = "org.hsqldb.jdbcDriver";
    private static final String XML_FILE = "usersDataSet.xml";
    private static final String TEST_FIRST_NAME = "Sam";
    private static final String TEST_LAST_NAME = "Smith";
    private static final String UPDATED_TEST_NAME = "Sam11";
    private HsqldbUserDao hsqldbUserDao;
    private ConnectionFactory connectionFactory;

    public void testCreate() {
        try {
            User user = createUserWithoutID();
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

    public void testFindAll() {
        try {
            User user = createUserWithoutID();
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

    public void testFind() throws DataBaseException {
        long testUserId = ID + 2;
        User expectedUser = hsqldbUserDao.create(createUserWithID(testUserId));
        User actualUser = hsqldbUserDao.find(testUserId);
        assertNotNull(actualUser);
        assertEquals(expectedUser.getFirstName(), actualUser.getFirstName());
        assertEquals(expectedUser.getLastName(), actualUser.getLastName());
    }

    public void testDelete() throws DataBaseException {
        User testUser = createUserWithID(ID);
        hsqldbUserDao.delete(testUser);
        assertNull(hsqldbUserDao.find(ID));
    }

    public void testUpdate() throws DataBaseException {
        User testUser = new User(ID, TEST_FIRST_NAME, TEST_LAST_NAME, new Date());
        hsqldbUserDao.create(testUser);

        testUser.setFirstName(UPDATED_TEST_NAME);

        hsqldbUserDao.update(testUser);
        User updatedUser = hsqldbUserDao.find(testUser.getId());
        assertNotNull(updatedUser);
        assertEquals(testUser.getFirstName(), updatedUser.getFirstName());
        assertEquals(testUser.getLastName(), updatedUser.getLastName());
    }

    @Override
    protected void setUp() throws Exception {
        connectionFactory = new ConnectionFactoryImpl(USER, PASSWORD, URL, DRIVER);
        hsqldbUserDao = new HsqldbUserDao(connectionFactory);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        connectionFactory = new ConnectionFactoryImpl(USER, PASSWORD, URL, DRIVER);
        return new DatabaseConnection(connectionFactory.createConnection());
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        IDataSet dataSet = new XmlDataSet(getClass().getClassLoader()
                                                    .getResourceAsStream(XML_FILE));
        return dataSet;
    }

    private static User createUserWithoutID() {
        User user = new User(null, FIRST_NAME, LAST_NAME, new Date());
        return user;
    }

    private static User createUserWithID(long id) {
        User user = new User(id, FIRST_NAME, LAST_NAME, new Date());
        return user;
    }
}
