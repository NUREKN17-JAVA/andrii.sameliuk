package ua.nure.cs.sameliuk.usermanagment.db;

import ua.nure.cs.sameliuk.usermanagment.domain.User;

import java.util.Collection;

/**
 * Describes the main opportunities of data transfer objects in the UserManagement application.
 *
 * @param <T>
 *         a bean class DTO of which needs to create
 */
public interface UserDao {

    /**
     * Creates a bean object.
     *
     * @param entity
     *         an object which needs to create
     */
    User create(User entity) throws DataBaseException;

    /**
     * Updates a bean object.
     *
     * @param entity
     *         an object which needs to update
     */
    void update(User entity) throws DataBaseException;

    /**
     * Deletes bean object.
     *
     * @param entity
     *         an object which needs to update
     */
    void delete(User entity) throws DataBaseException;

    /**
     * Finds an object in the database by its ID.
     *
     * @param id
     *         an identifier of object in database
     */
    User find(Long id) throws DataBaseException;

    /**
     * Obtains all elements from database.
     */
    Collection<User> findAll() throws DataBaseException;

    Collection find (String firstName, String lastName) throws DataBaseException;

    /**
     * Sets {@link ConnectionFactory}.
     */
    void setConnectionFactory(ConnectionFactory connectionFactory);
}
