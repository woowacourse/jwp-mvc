package nextstep.mvc.tobe.view;

public class JspViewResolver implements ViewResolver {

    @Override
    public boolean support(ViewType viewType) {
        return ViewType.JSP_VIEW == viewType;
    }

    @Override
    public View resolve(String viewName) {
        return new JspView(viewName);
    }
}
