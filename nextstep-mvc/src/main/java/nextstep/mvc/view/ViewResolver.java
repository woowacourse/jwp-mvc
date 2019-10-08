package nextstep.mvc.view;

public class ViewResolver {

    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    public static ModelAndView resolve(Object view) throws Exception {
        if (view instanceof View) {
            return new ModelAndView(conversion(view));
        }
        if (view instanceof String) {
            return new ModelAndView(processing(view));
        }
        return (ModelAndView) view;
    }

    private static View conversion(Object view) {
        return (View) view;
    }

    private static View processing(Object view) {
        String name = (String) view;
        if (name.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            return new RedirectView(name.substring(DEFAULT_REDIRECT_PREFIX.length()));
        }
        return new JspView(name);
    }
}
