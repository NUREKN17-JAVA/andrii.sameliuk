package ua.nure.cs.sameliuk.usermanagment.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DetailsServlet extends EditServlet {

    private static final String DETAILS_JSP = "/details.jsp";
    private static final String BROWSE_SERVLET = "/browse";
    private static final String BACK_BUTTON = "backButton";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (req.getParameter(BACK_BUTTON) != null) {
            req.getSession(true)
               .removeAttribute("user");
            req.getRequestDispatcher(BROWSE_SERVLET)
               .forward(req, resp);
        } else {
            req.getRequestDispatcher(DETAILS_JSP)
               .forward(req, resp);
        }
    }
}
