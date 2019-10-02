package nextstep.mvc;

import nextstep.mvc.tobe.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HandlerAdapter {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    public void handle(Object result, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (result instanceof String) {
            move((String) result, request, response);
            return;
        }
    }

    private void move(String viewName, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            resp.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
            return;
        }

        RequestDispatcher rd = req.getRequestDispatcher(viewName);
        rd.forward(req, resp);
    }
}
