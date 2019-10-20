package ua.nure.cs.sameliuk.usermanagment.db;

import ua.nure.cs.sameliuk.usermanagment.domain.User;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class HsqldbUserDao implements Dao<User> {

    private final ConnectionFactory connectionFactory;

    public static final String INSERT_QUERY = "INSERT INTO users (firstname, lastname, dateofbirth) VALUES (?, ?, ?)";

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
                    .prepareCall("call IDENTITY()");
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
