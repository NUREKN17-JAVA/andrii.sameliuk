package ua.nure.cs.sameliuk.usermanagment.web;

import ua.nure.cs.sameliuk.usermanagment.domain.User;

import java.util.Date;

import static junit.framework.Assert.assertNotNull;

public class EditServletTest extends MockServletTestCase {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        createServlet(EditServlet.class);
    }

    public void testEdit() {
        User user = new User(1000L, "John", "Doe", new Date());
        getMockUserDao().expect("update", user);

        addRequestParameter("id", "1000");
        addRequestParameter("firstName", "John");
        addRequestParameter("lastName", "Doe");
        addRequestParameter("date", String.valueOf(new Date()));
        addRequestParameter("okButton", "Ok");
        doPost();
    }

    public void testEditEmptyFirstName() {
        addRequestParameter("id", "1000");
        addRequestParameter("lastName", "Doe");
        addRequestParameter("date", String.valueOf(new Date()));
        addRequestParameter("okButton", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
    }

    public void testEditEmptyLastName() {
        addRequestParameter("id", "1000");
        addRequestParameter("firstName", "John");
        addRequestParameter("date", String.valueOf(new Date()));
        addRequestParameter("okButton", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
    }

    public void testEditEmptyEmptyDate() {
        addRequestParameter("id", "1000");
        addRequestParameter("firstName", "John");
        addRequestParameter("lastName", "Doe");
        addRequestParameter("okButton", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
    }

    public void testEditEmptyDataIncorect() {
        addRequestParameter("id", "1000");
        addRequestParameter("firstName", "John");
        addRequestParameter("lastName", "Doe");
        addRequestParameter("date", "123456789");
        addRequestParameter("okButton", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
    }
}
