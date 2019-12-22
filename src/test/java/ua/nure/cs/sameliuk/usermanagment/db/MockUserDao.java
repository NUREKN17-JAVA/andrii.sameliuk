package ua.nure.cs.sameliuk.usermanagment.db;

import ua.nure.cs.sameliuk.usermanagment.domain.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MockUserDao implements UserDao {

    private long id = 0;

    private Map users = new HashMap();

    @Override
    public User create(User entity) throws DataBaseException {
        Long currentId = new Long(++this.id);
        entity.setId(currentId);
        users.put(currentId, entity);
        return entity;
    }

    @Override
    public void update(User entity) throws DataBaseException {
        Long currentId = entity.getId();
        users.remove(currentId);
        users.put(currentId, entity);
    }

    @Override
    public void delete(User entity) throws DataBaseException {
        Long currentId = entity.getId();
        users.remove(currentId);
    }

    @Override
    public User find(Long id) throws DataBaseException {
        return (User) users.get(id);
    }

    @Override
    public Collection<User> findAll() throws DataBaseException {
        return users.values();
    }

    @Override
    public void setConnectionFactory(ConnectionFactory connectionFactory) {

    }

    @Override
    public Collection find(String firstName, String lastName) throws DataBaseException {
        throw new UnsupportedOperationException();
    }

}
