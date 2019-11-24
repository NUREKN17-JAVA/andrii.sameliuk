package ua.nure.cs.sameliuk.usermanagment.gui;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.finder.NamedComponentFinder;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import java.awt.Component;

public class MainFrameTest extends JFCTestCase {

    private MainFrame mainFrame;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        setHelper(new JFCTestHelper());
        mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }

    @Override
    public void tearDown() throws Exception {
        mainFrame.setVisible(false);
        getHelper();
        TestHelper.cleanUp(this);
        super.tearDown();
    }

    private Component find(Class componentClass, String name) {
        NamedComponentFinder finder = new NamedComponentFinder(componentClass, name);
        finder.setWait(0);
        Component component = finder.find(mainFrame, 0);
        assertNotNull("Could not find component '" + name + '\'', component);
        return component;
    }

    public void testBrowseControls() {
        find(JPanel.class, "browsePanel");
        find(JTable.class, "userTable");
        find(JButton.class, "addButton");
        find(JButton.class, "editButton");
        find(JButton.class, "deleteButton");
        find(JButton.class, "detailsButton");
    }
}
