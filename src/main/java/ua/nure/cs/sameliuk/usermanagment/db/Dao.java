package ua.nure.cs.sameliuk.usermanagment.db;

import java.util.Collection;

/**
 * Describes the main opportunities of data transfer objects in the UserManagement application.
 *
 * @param <T>
 *         a bean class DTO of which needs to create
 */
public interface Dao<T> {

    /**
     * Creates a bean object.
     *
     * @param entity
     *         an object which needs to create
     */
    T create(T entity) throws DataBaseException;

    /**
     * Updates a bean object.
     *
     * @param entity
     *         an object which needs to update
     */
    void update(T entity) throws DataBaseException;

    /**
     * Deletes bean object.
     *
     * @param entity
     *         an object which needs to update
     */
    void delete(T entity) throws DataBaseException;

    /**
     * Finds an object in the database by its ID.
     *
     * @param id
     *         an identifier of object in database
     */
    T find(Long id) throws DataBaseException;

    /**
     * Obtains all elements from database.
     */
    Collection<T> findAll() throws DataBaseException;

    /**
     * Sets {@link ConnectionFactory}.
     */
    void setConnectionFactory(ConnectionFactory connectionFactory);
}
