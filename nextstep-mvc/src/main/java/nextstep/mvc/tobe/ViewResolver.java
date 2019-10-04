package nextstep.mvc.tobe;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ViewResolver {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    public void resolve(Object view, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (view instanceof ModelAndView) {
            ((ModelAndView) view).render(request, response);
            return;
        }
        move((String) view, request, response);
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
