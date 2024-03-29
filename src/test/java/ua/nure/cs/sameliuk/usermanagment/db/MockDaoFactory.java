package ua.nure.cs.sameliuk.usermanagment.db;

import com.mockobjects.dynamic.Mock;

public class MockDaoFactory extends DaoFactory {

    private Mock mockUserDao;

    public MockDaoFactory() {
        mockUserDao = new Mock(UserDao.class);
    }

    public Mock getMockUserDao() {
        return mockUserDao;
    }

    @Override
    public UserDao getDao() throws ReflectiveOperationException {
        return (UserDao) mockUserDao.proxy();
    }
}
