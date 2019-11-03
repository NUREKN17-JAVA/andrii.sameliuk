package ua.nure.cs.sameliuk.usermanagment.db;

import java.sql.Connection;

/**
 * Describes factory for working with connection to the database.
 */
public interface ConnectionFactory {

    /**
     * Creates connection to database.
     */
    Connection createConnection() throws DataBaseException;
}
