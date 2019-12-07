package ua.nure.cs.sameliuk.usermanagment.db;

import com.mockobjects.dynamic.Mock;

public class MockDaoFactory extends DaoFactory {

    private Mock mockUserDao;

    public MockDaoFactory() {
        mockUserDao = new Mock(HsqldbUserDao.class);
    }

    public Mock getMockUserDao() {
        return mockUserDao;
    }

    @Override
    public Dao getDao() throws ReflectiveOperationException {
        return (Dao) mockUserDao.proxy();
    }
}
