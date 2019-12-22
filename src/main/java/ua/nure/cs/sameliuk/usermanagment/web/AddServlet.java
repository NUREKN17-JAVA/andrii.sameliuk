package ua.nure.cs.sameliuk.usermanagment.web;

import ua.nure.cs.sameliuk.usermanagment.db.DataBaseException;
import ua.nure.cs.sameliuk.usermanagment.domain.User;
import ua.nure.cs.sameliuk.usermanagment.db.DaoFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddServlet extends EditServlet {
    private static final String ADD_JSP = "/add,jsp";

    @Override
    protected void showPage(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        req.getRequestDispatcher(ADD_JSP).forward(req, resp);
    }

    @Override
    protected void processUser(User user)
            throws ReflectiveOperationException, DataBaseException {

        DaoFactory.getInstance().getDao().create(user);
    }
}
