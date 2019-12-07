package ua.nure.cs.sameliuk.usermanagment.gui;

import ua.nure.cs.sameliuk.usermanagment.db.DataBaseException;
import ua.nure.cs.sameliuk.usermanagment.domain.User;
import ua.nure.cs.sameliuk.usermanagment.util.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;

public class EditPanel extends JPanel implements ActionListener {

    private final static int FRAME_WIDTH = 400;
    private final static int FRAME_HEIGHT = 600;
    private static final String SUBMIT_COMMAND = "submit";
    private static final String CANCEL_COMMAND = "cancel";
    private static final String SUBMIT_BUTTON_COMPONENT_NAME = "submitButton";
    private static final String EDIT_PANEL_COMPONENT_NAME = "editPanel";
    private static final String CANCEL_BUTTON_COMPONENT_NAME = "cancelButton";
    private static final String FIRST_NAME_FIELD_COMPONENT_NAME = "firstNameField";
    private static final String LAST_NAME_FIELD_COMPONENT_NAME = "lastNameField";
    private static final String DATE_OF_BIRTH_FIELD_COMPONENT_NAME = "dateOfBirthField";
    private MainFrame parent;
    private JButton cancelButton;
    private JButton submitButton;
    private JPanel buttonPanel;
    private JPanel fieldPanel;
    private JTextField dateOfBirthField;
    private JTextField lastNameField;
    private JTextField firstNameField;
    private User user;

    EditPanel(MainFrame frame) {
        this.parent = frame;
        user = parent.getSelectedUser();
        initialize();
    }

    private void initialize() {
        this.setName(EDIT_PANEL_COMPONENT_NAME);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setLayout(new BorderLayout());
        this.add(getFieldPanel(), BorderLayout.NORTH);
        this.add(getButtonPanel(), BorderLayout.SOUTH);

    }

    private JPanel getFieldPanel() {
        if (fieldPanel == null) {
            fieldPanel = new JPanel();
            fieldPanel.setLayout(new GridLayout(3, 2));
            addLabelField(fieldPanel, Message.getString("name_label"), getFirstNameField());
            addLabelField(fieldPanel, Message.getString("surname_label"), getLastNameField());
            addLabelField(fieldPanel, Message.getString("date.of.birth_label"),
                          getDateOfBirthField());
        }

        return fieldPanel;
    }

    private JTextField getDateOfBirthField() {
        if (dateOfBirthField == null) {
            dateOfBirthField = new JTextField();
            dateOfBirthField.setName(DATE_OF_BIRTH_FIELD_COMPONENT_NAME);
        }

        return dateOfBirthField;
    }

    private JTextField getLastNameField() {
        if (lastNameField == null) {
            lastNameField = new JTextField();
            lastNameField.setName(LAST_NAME_FIELD_COMPONENT_NAME);
        }

        return lastNameField;
    }

    private void addLabelField(JPanel fieldPanel, String labelText, JTextField textField) {
        JLabel label = new JLabel(labelText);
        label.setLabelFor(textField);
        fieldPanel.add(label);
        fieldPanel.add(textField);
    }

    private JTextField getFirstNameField() {
        if (firstNameField == null) {
            firstNameField = new JTextField();
            firstNameField.setName(FIRST_NAME_FIELD_COMPONENT_NAME);
        }

        return firstNameField;
    }

    private JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            buttonPanel.add(getOkButton(), null);
            buttonPanel.add(getCancelButton(), null);
        }

        return buttonPanel;
    }

    private JButton getCancelButton() {
        if (cancelButton == null) {
            cancelButton = new JButton();
            cancelButton.setName(CANCEL_BUTTON_COMPONENT_NAME);
            cancelButton.setText(Message.getString("cancel_button"));
            cancelButton.setActionCommand(CANCEL_COMMAND);
            cancelButton.addActionListener(this);
        }

        return cancelButton;
    }

    private JButton getOkButton() {
        if (submitButton == null) {
            submitButton = new JButton();
            submitButton.setName(SUBMIT_BUTTON_COMPONENT_NAME);
            submitButton.setText(Message.getString("submit_edit_button"));
            submitButton.setActionCommand(SUBMIT_COMMAND);
            submitButton.addActionListener(this);
        }

        return submitButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        label:
        if (SUBMIT_COMMAND.equalsIgnoreCase(e.getActionCommand())) {
            String firstName = getFirstNameField().getText();
            String lastName = getLastNameField().getText();
            String dateOfBirth = getDateOfBirthField().getText();

            if (!firstName.isEmpty() && !lastName.isEmpty() && !dateOfBirth.isEmpty()) {
                user.setFirstName(getFirstNameField().getText());
                user.setLastName(getLastNameField().getText());
                DateFormat format = DateFormat.getDateInstance();
                try {
                    user.setDateOfBirth(format.parse(getDateOfBirthField().getText()));
                } catch (ParseException e1) {
                    JOptionPane.showMessageDialog(this, e1.getMessage(), "Error",
                                                  JOptionPane.ERROR_MESSAGE);
                    getDateOfBirthField().setBackground(Color.RED);
                    break label;
                }

                try {
                    parent.getDao()
                          .update(user);
                } catch (DataBaseException e1) {
                    JOptionPane.showMessageDialog(this, e1.getMessage(), "Error",
                                                  JOptionPane.ERROR_MESSAGE);
                }
                clearFields();
                this.setVisible(false);
                parent.showBrowsePanel();
            } else {
                String message = "Please, fill all fields";
                JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
                getFirstNameField().setBackground(Color.RED);
                getLastNameField().setBackground(Color.RED);
                getDateOfBirthField().setBackground(Color.RED);
            }
        } else if (CANCEL_COMMAND.equalsIgnoreCase(e.getActionCommand())) {
            clearFields();
            this.setVisible(false);
            parent.showBrowsePanel();
        }
    }

    private void clearFields() {
        getFirstNameField().setText("");
        getFirstNameField().setBackground(Color.WHITE);

        getLastNameField().setText("");
        getLastNameField().setBackground(Color.WHITE);

        getDateOfBirthField().setText("");
        getDateOfBirthField().setBackground(Color.WHITE);
    }
}
