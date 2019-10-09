package nextstep.mvc.tobe.view;

public class JspViewResolver implements ViewResolver {
    private static final String REDIRECT_PREFIX = "redirect:";
    private static final String JSP_SUFFIX = ".jsp";

    @Override
    public boolean supports(ModelAndView mv) {
        String viewName = mv.getViewName();
        return viewName.startsWith(REDIRECT_PREFIX) || viewName.endsWith(JSP_SUFFIX);
    }

    @Override
    public View resolve(ModelAndView mv) {
        return new JspView(mv.getViewName());
    }
}
