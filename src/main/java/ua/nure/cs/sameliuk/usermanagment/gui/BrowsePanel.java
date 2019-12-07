package ua.nure.cs.sameliuk.usermanagment.gui;

import ua.nure.cs.sameliuk.usermanagment.db.DataBaseException;
import ua.nure.cs.sameliuk.usermanagment.domain.User;
import ua.nure.cs.sameliuk.usermanagment.util.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BrowsePanel extends JPanel implements ActionListener {

    private static final String ADD_COMMAND = "add";
    private static final String EDIT_COMMAND = "edit";
    private static final String DELETE_COMMAND = "delete";
    private static final String DETAIL_COMMAND = "detail";
    private final MainFrame parent;
    private static final String USER_TABLE_COMPONENT_NAME = "userTable";
    private static final String EDIT_BUTTON_COMPONENT_NAME = "editButton";
    private static final String BROWSE_PANEL_COMPONENT_NAME = "browsePanel";
    private static final String ADD_BUTTON_COMPONENT_NAME = "addButton";
    private static final String DELETE_BUTTON_COMPONENT_NAME = "deleteButton";
    private static final String DETAIL_BUTTON_COMPONENT_NAME = "detailButton";
    private JScrollPane tablePanel;
    private JTable userTable;
    private JPanel buttonPanel;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton detailButton;

    public BrowsePanel(MainFrame mainFrame) {
        parent = mainFrame;
        initialize();
    }

    private void initialize() {
        this.setName(BROWSE_PANEL_COMPONENT_NAME);
        this.setLayout(new BorderLayout());
        this.add(getTablePanel(), BorderLayout.CENTER);
        this.add(getButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            buttonPanel.add(getAddButton(), null);
            buttonPanel.add(getEditButton(), null);
            buttonPanel.add(getDeleteButton(), null);
            buttonPanel.add(getDetailsButton(), null);
        }
        return buttonPanel;
    }

    private JButton getDetailsButton() {
        if (detailButton == null) {
            detailButton = new JButton();
            detailButton.setText(Message.getString("user.details_button"));
            detailButton.setName(DETAIL_BUTTON_COMPONENT_NAME);
            detailButton.setActionCommand(DETAIL_COMMAND);
            detailButton.addActionListener(this);
        }
        return detailButton;
    }

    private JButton getDeleteButton() {
        if (deleteButton == null) {
            deleteButton = new JButton();
            deleteButton.setText(Message.getString("delete.user_button"));
            deleteButton.setName(DELETE_BUTTON_COMPONENT_NAME);
            deleteButton.setActionCommand(DELETE_COMMAND);
            deleteButton.addActionListener(this);
        }
        return deleteButton;
    }

    private JButton getEditButton() {
        if (editButton == null) {
            editButton = new JButton();
            editButton.setText(Message.getString("edit.user_button"));
            editButton.setName(EDIT_BUTTON_COMPONENT_NAME);
            editButton.setActionCommand(EDIT_COMMAND);
            editButton.addActionListener(this);
        }
        return editButton;
    }

    private JButton getAddButton() {
        if (addButton == null) {
            addButton = new JButton();
            addButton.setText(Message.getString("add.user_button"));
            addButton.setName(ADD_BUTTON_COMPONENT_NAME);
            addButton.setActionCommand(ADD_COMMAND);
            addButton.addActionListener(this);
        }
        return addButton;
    }

    private JScrollPane getTablePanel() {
        if (tablePanel == null) {
            tablePanel = new JScrollPane(getUserTable());
        }
        return tablePanel;
    }

    private JTable getUserTable() {
        if (userTable == null) {
            userTable = new JTable();
            userTable.setName(USER_TABLE_COMPONENT_NAME);
        }
        return userTable;
    }

    public void initTable() {
        UserTableModel tableModel;

        try {
            tableModel = new UserTableModel(parent.getUserDao()
                                                  .findAll());
        } catch (DataBaseException e) {
            tableModel = new UserTableModel(new ArrayList());
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        getUserTable().setModel(tableModel);
    }

    public User getSelectedUser() {
        int selectedRow = getUserTable().getSelectedRow();
        int selectedColumn = 0;

        Long userId = null;
        User user = null;

        if (selectedRow == -1) {
            String message = "Please, select row of user, details of whom you want to see";
            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                userId = (Long) userTable.getValueAt(userTable.getSelectedRow(), selectedColumn);
                user = (User) parent.getUserDao()
                                    .find(userId);
            } catch (DataBaseException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error",
                                              JOptionPane.ERROR_MESSAGE);
            }
        }
        return user;
    }

    private void deleteUser(User user) {
        String message = "Are you sure you want to delete this user?";
        int result = JOptionPane.showConfirmDialog(this,
                                                   message,
                                                   "Confirm deleting",
                                                   JOptionPane.YES_NO_OPTION,
                                                   JOptionPane.WARNING_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            try {
                parent.getUserDao()
                      .delete(user);
                getUserTable().setModel(new UserTableModel(parent.getUserDao()
                                                                 .findAll()));
            } catch (DataBaseException e1) {
                JOptionPane.showMessageDialog(this, e1.getMessage(), "Error",
                                              JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if (ADD_COMMAND.equalsIgnoreCase(actionCommand)) {
            this.setVisible(false);
            parent.showAddPanel();
        } else if (DETAIL_COMMAND.equalsIgnoreCase(actionCommand)) {
            User selectedUser = getSelectedUser();
            JOptionPane.showMessageDialog(this, selectedUser.toString(), "User info",
                                          JOptionPane.INFORMATION_MESSAGE);
        } else if (DELETE_COMMAND.equalsIgnoreCase(actionCommand)) {
            User selectedUser = getSelectedUser();

            if (selectedUser != null) {
                deleteUser(selectedUser);
            }

        } else if (EDIT_COMMAND.equalsIgnoreCase(actionCommand)) {
            int selectedRow = userTable.getSelectedRow();
            int selectedColumn = userTable.getSelectedColumn();

            if (selectedRow != -1 || selectedColumn != -1) {
                this.setVisible(false);
                parent.showEditPanel();
            } else {
                JOptionPane.showMessageDialog(this,
                                              "Please, select row of user, details of whom you want to see",
                                              "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
