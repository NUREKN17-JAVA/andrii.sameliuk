package ua.nure.cs.sameliuk.usermanagment.domain.db;

import ua.nure.cs.sameliuk.usermanagment.domain.User;

import java.util.Collection;

public class HsqldbUserDao implements Dao<User> {

    @Override
    public User create(User entity) throws DataBaseException {
        return null;
    }

    @Override
    public void update(User entity) throws DataBaseException {

    }

    @Override
    public void delete(User entity) throws DataBaseException {

    }

    @Override
    public User find(Long id) throws DataBaseException {
        return null;
    }

    @Override
    public Collection<User> findAll() throws DataBaseException {
        return null;
    }
}
