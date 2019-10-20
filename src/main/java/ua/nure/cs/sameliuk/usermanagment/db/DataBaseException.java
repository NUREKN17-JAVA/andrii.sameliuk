package ua.nure.cs.sameliuk.usermanagment.db;

import java.sql.SQLException;

public class DataBaseException extends Exception {

    public DataBaseException(SQLException e) {
        super(e);
    }

    public DataBaseException(String s) {
        super(s);
    }
}
