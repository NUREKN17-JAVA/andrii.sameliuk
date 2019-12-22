package ua.nure.cs.sameliuk.usermanagment.web;

import ua.nure.cs.sameliuk.usermanagment.db.DaoFactory;
import ua.nure.cs.sameliuk.usermanagment.db.DataBaseException;
import ua.nure.cs.sameliuk.usermanagment.domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class BrowseServlet extends HttpServlet {
    private static final String DETAILS_BUTTON = "detailsButton";
    private static final String DELETE_BUTTON = "deleteButton";
    private static final String DETAILS_JSP = "/details.jsp";
    private static final String BROWSE_JSP = "/browse.jsp";
    private static final String EDIT_BUTTON = "editButton";
    private static final String ADD_BUTTON = "addButton";
    private static final String EDIT_JSP = "/edit.jsp";
    private static final String ADD_JSP = "/add.jsp";
    private static final String ERROR_TAG = "error";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws
                                                                             ServletException,
                                                                             IOException {
        if (req.getParameter(ADD_BUTTON) != null) {
            add(req, resp);
        } else if (req.getParameter(EDIT_BUTTON) != null) {
            edit(req, resp);
        } else if (req.getParameter(DELETE_BUTTON) != null) {
            delete(req, resp);
        } else if (req.getParameter(DETAILS_BUTTON) != null) {
            details(req, resp);
        } else {
            browse(req, resp);
        }
    }

    private void details(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        String idString = getUserId(req, resp);
        if (idString == null) {
            return;
        }

        try {
            User user = DaoFactory.getInstance().getDao().find(Long.valueOf(idString));
            req.getSession().setAttribute("user", user);
        } catch (ReflectiveOperationException | DataBaseException e) {
            req.setAttribute(ERROR_TAG, "ERROR: " + e.toString());
            req.getRequestDispatcher(BROWSE_JSP).forward(req, resp);
            return;
        }

        req.getRequestDispatcher(DETAILS_JSP).forward(req, resp);
    }

    private String getUserId(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idString = req.getParameter("id");
        if (idString == null || idString.trim().length() == 0) {
            req.setAttribute(ERROR_TAG, "You must select user");
            req.getRequestDispatcher(BROWSE_JSP).forward(req, resp);
            return null;
        }

        return idString;
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        String idString = getUserId(req, resp);
        if (idString == null) {
            return;
        }

        try {
            User user = DaoFactory.getInstance().getDao().find(Long.valueOf(idString));
            DaoFactory.getInstance().getDao().delete(user);
        } catch (ReflectiveOperationException | DataBaseException e) {
            req.setAttribute(ERROR_TAG, "ERROR: " + e.toString());
        }
        browse(req, resp);
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        String idString = getUserId(req, resp);
        if (idString == null) {
            return;
        }

        try {
            User user = DaoFactory.getInstance().getDao().find(Long.valueOf(idString));
            req.getSession().setAttribute("user", user);
        } catch (Exception e) {
            req.setAttribute(ERROR_TAG, "ERROR: " + e.toString());
            req.getRequestDispatcher(BROWSE_JSP).forward(req, resp);
            return;
        }

        req.getRequestDispatcher(EDIT_JSP).forward(req, resp);
    }

    private void add(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        req.getRequestDispatcher(ADD_JSP).forward(req, resp);
    }

    private void browse(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Collection users = DaoFactory.getInstance().getDao().findAll();
            req.getSession().setAttribute("users", users);
            req.getRequestDispatcher(BROWSE_JSP).forward(req, resp);
        } catch (ReflectiveOperationException | DataBaseException e) {
            throw new ServletException(e);
        }
    }
}
