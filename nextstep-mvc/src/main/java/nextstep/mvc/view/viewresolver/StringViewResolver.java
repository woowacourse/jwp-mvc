package nextstep.mvc.view.viewresolver;

import nextstep.mvc.exception.NotSupportedViewTypeException;
import nextstep.mvc.view.JspView;
import nextstep.mvc.view.ModelAndView;
import nextstep.mvc.view.RedirectView;

public class StringViewResolver implements ViewResolver {

    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";
    private static final String DEFAULT_JSP_EXTENSION = ".jsp";
    private static final String NOT_SUPPORTED_VIEW_TYPE_ERROR = "지원하지 않는 View 형식";

    @Override
    public boolean canResolve(Object view) {
        return view instanceof String;
    }

    @Override
    public ModelAndView resolve(Object view) {
        String name = (String) view;
        if (name.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            return new ModelAndView(new RedirectView(name.substring(DEFAULT_REDIRECT_PREFIX.length())));
        }
        if (name.endsWith(DEFAULT_JSP_EXTENSION)) {
            return new ModelAndView(new JspView(name));
        }
        throw new NotSupportedViewTypeException(NOT_SUPPORTED_VIEW_TYPE_ERROR);
    }
}
