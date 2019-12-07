package ua.nure.cs.sameliuk.usermanagment.gui;

import ua.nure.cs.sameliuk.usermanagment.domain.User;
import ua.nure.cs.sameliuk.usermanagment.util.Message;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserTableModel extends AbstractTableModel {

    private static final String[] COLUMN_NAMES = {Message.getString("id"),
            Message.getString("name_label"), Message.getString("surname_label")};

    private static final Class[] COLUMN_CLASSES = {Long.class, String.class, String.class};

    private List users = null;

    public UserTableModel(Collection users) {
        this.users = new ArrayList(users);
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        return COLUMN_CLASSES[columnIndex];
    }


    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User user = (User) users.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return user.getId();
            case 1:
                return user.getFirstName();
            case 2:
                return user.getLastName();
            default:
                throw new IllegalStateException("Unexpected value: " + columnIndex);
        }
    }
}
