package nextstep.mvc.tobe.viewresolver;

import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.View;

import static nextstep.mvc.tobe.viewresolver.RedirectViewResolver.DEFAULT_REDIRECT_PREFIX;

public class JspViewResolver extends InternalResourceViewResolver {
    private static final String DEFAULT_PREFIX = "";
    private static final String DEFAULT_SUFFIX = ".jsp";

    public JspViewResolver() {
        this(DEFAULT_PREFIX, DEFAULT_SUFFIX);
    }

    public JspViewResolver(final String prefix, final String suffix) {
        super(prefix, suffix);
    }

    @Override
    public boolean supports(final Object view) {
        return view instanceof String && !((String) view).startsWith(DEFAULT_REDIRECT_PREFIX);
    }

    @Override
    public View resolveView(final Object view) {
        final String viewName = DEFAULT_PREFIX + view + DEFAULT_SUFFIX;
        return new JspView(viewName);
    }
}
