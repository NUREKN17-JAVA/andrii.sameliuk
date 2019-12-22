package ua.nure.cs.sameliuk.usermanagment.web;

import ua.nure.cs.sameliuk.usermanagment.domain.User;

import java.util.Date;

public class AddServletTest extends MockServletTestCase {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        createServlet(AddServlet.class);
    }

    public void testAdd() {
        User user = new User(1000L, "John", "Doe", new Date());
        User newUser = new User("John", "Doe", new Date());
        getMockUserDao().expectAndReturn("create", newUser, user);

        addRequestParameter("firstName", "John");
        addRequestParameter("lastName", "Doe");
        addRequestParameter("date", String.valueOf(new Date()));
        addRequestParameter("okButton", "Ok");
        doPost();
    }

    public void testAddEmptyFirstName() {
        addRequestParameter("lastName", "Doe");
        addRequestParameter("date", String.valueOf(new Date()));
        addRequestParameter("okButton", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest()
                                                                .getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
    }

    public void testAddEmptyLastName() {
        addRequestParameter("firstName", "John");
        addRequestParameter("date", String.valueOf(new Date()));
        addRequestParameter("okButton", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest()
                                                                .getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
    }

    public void testAddEmptyEmptyDate() {
        addRequestParameter("firstName", "John");
        addRequestParameter("lastName", "Doe");
        addRequestParameter("okButton", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest()
                                                                .getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
    }

    public void testAddEmptyDataIncorect() {
        addRequestParameter("firstName", "John");
        addRequestParameter("lastName", "Doe");
        addRequestParameter("date", "123456789");
        addRequestParameter("okButton", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest()
                                                                .getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
    }
}
