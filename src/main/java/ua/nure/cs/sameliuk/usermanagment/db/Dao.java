package ua.nure.cs.sameliuk.usermanagment.db;

import java.util.Collection;

public interface Dao<T>{

    T create(T entity) throws DataBaseException;

    void update(T entity) throws DataBaseException;

    void delete(T entity) throws DataBaseException;

    T find(Long id) throws DataBaseException;

    Collection<T> findAll() throws DataBaseException;
}
