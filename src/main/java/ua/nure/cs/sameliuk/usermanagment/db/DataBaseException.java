package ua.nure.cs.sameliuk.usermanagment.db;

import java.sql.SQLException;

/**
 * Thrown if during working with database something goes wrong.
 *
 * <p>For example, the element can't be deleted from the database.
 */
public class DataBaseException extends Exception {

    private static final long serialVersionUID = 8579353980884247904L;

    public DataBaseException(SQLException e) {
        super(e);
    }

    public DataBaseException(String s) {
        super(s);
    }
}
