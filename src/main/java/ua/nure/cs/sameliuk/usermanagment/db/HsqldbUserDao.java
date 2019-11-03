package ua.nure.cs.sameliuk.usermanagment.db;

import ua.nure.cs.sameliuk.usermanagment.domain.User;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * An implementation of {@link Dao} for {link User} bean.
 */
class HsqldbUserDao implements Dao<User> {

    private ConnectionFactory connectionFactory;

    private static final String CALL_IDENTITY = "call IDENTITY()";

    private static final String INSERT_QUERY
            = "INSERT INTO users (firstname, lastname, dateofbirth) VALUES (?, ?, ?)";

    private String FIND_ALL_USERS = "SELECT * FROM users";

    private final String FIND_BY_ID = "SELECT * FROM USERS WHERE id = ?";

    private final String DELETE_USER = "DELETE FROM USERS WHERE id = ?";

    private final String UPDATE_USER
            = "UPDATE USERS SET firstname = ?, lastname = ?, dateofbirth = ? WHERE id = ?";

    /**
     * Initializes a {@code HsqldbUserDao}.
     *
     * @param factory
     *         a factory for working with connection to database
     */
    public HsqldbUserDao(ConnectionFactory factory) {
        connectionFactory = factory;
    }

    @Override
    public User create(User entity) throws DataBaseException {
        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setDate(3, new Date(entity.getDateOfBirth()
                                                .getTime()));
            int numberOfRows = statement.executeUpdate();
            if (numberOfRows != 1) {
                throw new DataBaseException("Number of inserted rows: " + numberOfRows);
            }
            CallableStatement callableStatement = connection
                    .prepareCall(CALL_IDENTITY);
            ResultSet keys = callableStatement.executeQuery();
            if (keys.next()) {
                entity.setId(keys.getLong(1));
            }
            keys.close();
            callableStatement.close();
            statement.close();
            connection.close();
            return entity;
        } catch (DataBaseException e) {
            throw e;
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public void update(User entity) throws DataBaseException {
        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER);
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setDate(3, new Date(entity.getDateOfBirth()
                                                        .getTime()));
            preparedStatement.setLong(4, entity.getId());

            int insertedRows = preparedStatement.executeUpdate();

            if (insertedRows != 1) {
                throw new DataBaseException("Number of inserted rows: " + insertedRows);
            }

            connection.close();
            preparedStatement.close();
        } catch (DataBaseException | SQLException e) {
            throw new DataBaseException(e.toString());
        }
    }

    @Override
    public void delete(User entity) throws DataBaseException {
        try {
            Connection connection = connectionFactory.createConnection();

            PreparedStatement statement = connection.prepareStatement(DELETE_USER);
            statement.setLong(1, entity.getId());

            int removedRows = statement.executeUpdate();

            if (removedRows != 1) {
                throw new DataBaseException("Number of removed rows: " + removedRows);
            }

            connection.close();
            statement.close();
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public User find(Long id) throws DataBaseException {
        User user = null;
        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong(1));
                user.setFirstName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setDateOfBirth(resultSet.getDate(4));
            }
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return user;
    }

    @Override
    public Collection<User> findAll() throws DataBaseException {
        Collection result = new ArrayList<>();
        try {
            Connection connection = connectionFactory.createConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_USERS);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setFirstName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setDateOfBirth(resultSet.getDate(4));
                result.add(user);
            }
        } catch (DataBaseException e) {
            throw e;
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }

        return result;
    }

    @Override
    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }
}
