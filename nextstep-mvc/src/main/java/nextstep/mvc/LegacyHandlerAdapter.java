package nextstep.mvc;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LegacyHandlerAdapter implements HandlerAdapter {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private HandlerMapping legacyHandlerMapping;

    public LegacyHandlerAdapter(HandlerMapping legacyHandlerMapping) {
        this.legacyHandlerMapping = legacyHandlerMapping;
    }

    @Override
    public boolean isSupported(HttpServletRequest request) {
        return false;
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String viewName = ((Controller) legacyHandlerMapping.getHandler(request)).execute(request, response);
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            response.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
            return null;
        }

        RequestDispatcher rd = request.getRequestDispatcher(viewName);
        rd.forward(request, response);
        return null;
    }
}
