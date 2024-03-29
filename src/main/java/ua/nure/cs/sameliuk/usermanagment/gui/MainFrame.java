package ua.nure.cs.sameliuk.usermanagment.gui;

import ua.nure.cs.sameliuk.usermanagment.db.UserDao;
import ua.nure.cs.sameliuk.usermanagment.db.DaoFactory;
import ua.nure.cs.sameliuk.usermanagment.domain.User;
import ua.nure.cs.sameliuk.usermanagment.util.Message;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    private JPanel contentPanel;
    private JPanel browsePanel;
    private AddPanel addPanel;
    private UserDao userDao;
    private EditPanel editPanel;

    public MainFrame() throws ReflectiveOperationException {
        super();
        userDao = DaoFactory.getInstance().getDao();
        initialize();
    }

    private void initialize() {
        this.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle(Message.getString("user_management"));
        this.setContentPane(getContentPanel());
    }

    private JPanel getContentPanel() {
        if (contentPanel == null) {
            contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(getBrowsePanel(), BorderLayout.CENTER);
        }

        return contentPanel;
    }

    private JPanel getBrowsePanel() {
        if (browsePanel == null) {
            browsePanel = new BrowsePanel(this);
        }

        ((BrowsePanel) browsePanel).initTable();
        return browsePanel;
    }

    public static void main(String[] args) throws ReflectiveOperationException {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }

    public void showAddPanel() {
        showPanel(getAddPanel());
    }

    private void showPanel(JPanel panel) {
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setVisible(true);
        panel.repaint();
    }

    public void showBrowsePanel() {
        showPanel(getBrowsePanel());
    }

    private AddPanel getAddPanel() {
        if (addPanel == null) {
            addPanel = new AddPanel(this);
        }
        return addPanel;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void showEditPanel() {
        showPanel(getEditPanel());
    }

    private EditPanel getEditPanel() {
        if (editPanel == null) {
            editPanel = new EditPanel(this);
        }
        return editPanel;
    }

    public User getSelectedUser() {
        return ((BrowsePanel)browsePanel).getSelectedUser();
    }
}
