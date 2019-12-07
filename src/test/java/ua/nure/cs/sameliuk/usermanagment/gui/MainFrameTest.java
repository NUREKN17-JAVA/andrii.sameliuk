package ua.nure.cs.sameliuk.usermanagment.gui;

import com.mockobjects.dynamic.Mock;
import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.cs.sameliuk.usermanagment.db.DaoFactory;
import ua.nure.cs.sameliuk.usermanagment.db.MockDaoFactory;
import ua.nure.cs.sameliuk.usermanagment.util.Message;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class MainFrameTest extends JFCTestCase {

    private static final String BROWSE_PANEL_COMPONENT_NAME = "browsePanel";
    private static final String USER_TABLE_COMPONENT_NAME = "userTable";
    private static final String ADD_BUTTON_COMPONENT_NAME = "addButton";
    private static final String EDIT_BUTTON_COMPONENT_NAME = "editButton";
    private static final String DELETE_BUTTON_COMPONENT_NAME = "deleteButton";
    private static final String DETAIL_BUTTON_COMPONENT_NAME = "detailButton";
    private MainFrame mainFrame;

    private Mock mockUserDao;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        Properties properties = new Properties();
      /*  properties.setProperty("dao.UserDao",
                               MockUserDao.class.getName());
      */
        properties.setProperty("dao.factory", MockDaoFactory.class.getName());

        DaoFactory.init(properties);
        mockUserDao = ((MockDaoFactory) DaoFactory.getInstance()).getMockUserDao();

        mockUserDao.expectAndReturn("findAll", new ArrayList());
        setHelper(new JFCTestHelper());
        mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }

    @Override
    public void tearDown() throws Exception {
        try {
            mockUserDao.verify();
            mainFrame.setVisible(false);
            TestHelper.cleanUp(this);
            super.tearDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Component find(Class componentClass, String name) {
        NamedComponentFinder finder = new NamedComponentFinder(componentClass, name);
        finder.setWait(0);
        Component component = finder.find(mainFrame, 0);
        assertNotNull("Could not find component '" + name + '\'', component);
        return component;
    }

    public void testBrowseControls() {
        find(JPanel.class, BROWSE_PANEL_COMPONENT_NAME);
        find(JButton.class, ADD_BUTTON_COMPONENT_NAME);
        find(JButton.class, EDIT_BUTTON_COMPONENT_NAME);
        find(JButton.class, DELETE_BUTTON_COMPONENT_NAME);
        find(JButton.class, DETAIL_BUTTON_COMPONENT_NAME);

        int expectedRowCount = 3;
        String expectedFirstColumn = Message.getString("id");
        String expectedSecondColumn = Message.getString("name_label");
        String expectedThirdColumn = Message.getString("surname_label");

        JTable table = (JTable) find(JTable.class, USER_TABLE_COMPONENT_NAME);

        assertEquals(expectedRowCount, table.getColumnCount());

        assertEquals(expectedFirstColumn, table.getColumnName(0));
        assertEquals(expectedSecondColumn, table.getColumnName(1));
        assertEquals(expectedThirdColumn, table.getColumnName(2));
    }

    public void testAddUser() {
        JTable table = (JTable) find(JTable.class, USER_TABLE_COMPONENT_NAME);
        assertEquals(0, table.getRowCount());

        JButton addButton = (JButton) find(JButton.class, ADD_BUTTON_COMPONENT_NAME);
        getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

        find(JPanel.class, "addPanel");

        JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
        JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
        JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");
        JButton okButton = (JButton) find(JButton.class, "okButton");
        find(JButton.class, "cancelButton");

        getHelper().sendString(new StringEventData(this, firstNameField, "John"));
        getHelper().sendString(new StringEventData(this, lastNameField, "Doe"));

        DateFormat formatter = DateFormat.getDateInstance();
        String date = formatter.format(new Date());
        getHelper().sendString(new StringEventData(this, dateOfBirthField, date));

        getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

        find(JPanel.class, "browsePanel");
        table = (JTable) find(JTable.class, USER_TABLE_COMPONENT_NAME);
        assertEquals(1, table.getRowCount());
    }
}
