package nextstep.mvc.tobe.view;

import nextstep.mvc.tobe.RequestContext;

import java.util.Map;

public class RedirectView implements View {
    private final String viewName;

    public RedirectView(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public void render(Map<String, ?> model, RequestContext requestContext) throws Exception {
        requestContext.getHttpServletResponse().sendRedirect(viewName);
    }
}
