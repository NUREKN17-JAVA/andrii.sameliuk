package ua.nure.cs.sameliuk.usermanagment.web;

import ua.nure.cs.sameliuk.usermanagment.domain.User;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertSame;

public class BrowseServletTest extends MockServletTestCase {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        createServlet(BrowseServlet.class);
    }

    public void testBrowse() {
        User user = new User(1000L, "John", "Doe", new Date());
        List list = Collections.singletonList(user);
        getMockUserDao().expectAndReturn("findAll", list);

        doGet();

        Collection sessionCollection = (Collection) getWebMockObjectFactory().getMockSession()
                                                                             .getAttribute("users");
        assertNotNull("Could not find users list in session!", sessionCollection);
        assertSame(list, sessionCollection);
    }

    public void testEdit() {
        User user = new User(1000L, "John", "Doe", new Date());

        getMockUserDao().expectAndReturn("find", 1000L, user);
        addRequestParameter("editButton", "edit");
        addRequestParameter("id", "1000");
        doPost();
        User userInSession = (User) getWebMockObjectFactory().getMockSession()
                                                             .getAttribute("user");
        assertNotNull("Could not find user", user);
        assertSame(user, userInSession);
    }
}
