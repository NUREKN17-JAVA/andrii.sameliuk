package ua.nure.cs.sameliuk.usermanagment.db;

import java.sql.Connection;

public interface ConnectionFactory {
    Connection createConnection() throws DataBaseException;
}
