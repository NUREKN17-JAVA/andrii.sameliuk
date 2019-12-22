package ua.nure.cs.sameliuk.usermanagment.web;

import ua.nure.cs.sameliuk.usermanagment.db.DaoFactory;
import ua.nure.cs.sameliuk.usermanagment.db.DataBaseException;
import ua.nure.cs.sameliuk.usermanagment.domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.text.DateFormat;

public class EditServlet extends HttpServlet {

    private static final String CANCEL_BUTTON = "cancelButton";
    private static final String BROWSE_SERVLET = "/browse";
    private static final String BROWSE_JSP = "/browse.jsp";
    private static final String EDIT_JSP = "/edit.jsp";
    private static final String OK_BUTTON = "okButton";
    private static final String ERROR_TAG = "error";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (req.getParameter(OK_BUTTON) != null) {
            doOk(req, resp);
        } else if (req.getParameter(CANCEL_BUTTON) != null) {
            doCancel(req, resp);
        } else {
            showPage(req, resp);
        }
    }

    protected void showPage(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        req.getRequestDispatcher(EDIT_JSP)
           .forward(req, resp);
    }

    private void doCancel(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        req.getRequestDispatcher(BROWSE_JSP)
           .forward(req, resp);
    }

    private void doOk(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        User user = null;
        try {
            user = getUser(req);
        } catch (ValidationException e) {
            req.setAttribute(ERROR_TAG, e.getMessage());
            showPage(req, resp);
            return;
        }

        try {
            processUser(user);
        } catch (ReflectiveOperationException | DataBaseException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }

        req.getRequestDispatcher(BROWSE_SERVLET)
           .forward(req, resp);
    }

    protected void processUser(User user) throws ReflectiveOperationException, DataBaseException {
        DaoFactory.getInstance()
                  .getDao()
                  .update(user);
    }

    private User getUser(HttpServletRequest req) throws ValidationException {
        User user = new User();
        String idString = req.getParameter("id");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String dateString = req.getParameter("date");

        if (firstName == null) {
            throw new ValidationException("First name is empty!");
        }

        if (lastName == null) {
            throw new ValidationException("Last name is empty!");
        }

        if (dateString == null) {
            throw new ValidationException("Date is empty!");
        }

        if (idString != null) {
            user.setId(Long.valueOf(idString));
        }
        user.setFirstName(firstName);
        user.setLastName(lastName);
        try {
            user.setDateOfBirth(DateFormat.getDateInstance()
                                          .parse(dateString));
        } catch (Exception e) {
            throw new ValidationException("Date format is incorrect");
        }

        return user;
    }
}
