package nextstep.mvc.handleradapter;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.handleradapter.HandlerAdapter;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LegacyHandlerAdapter implements HandlerAdapter {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    @Override
    public boolean canHandle(Object handler) {
        boolean truth = handler instanceof Controller;
        return truth;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Controller castHandler = (Controller) handler;
        String viewName = castHandler.execute(request, response);
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            return new ModelAndView(new RedirectView(viewName));
        }
        return new ModelAndView(new JspView(viewName));
    }

//    @Override
//    public void handle(Object result, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        move((String) result, request, response);
//    }
//
//    private void move(String viewName, HttpServletRequest req, HttpServletResponse resp)
//            throws ServletException, IOException {
//        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
//            resp.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
//            return;
//        }
//
//        RequestDispatcher rd = req.getRequestDispatcher(viewName);
//        rd.forward(req, resp);
//    }
}
