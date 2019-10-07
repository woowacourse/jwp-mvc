package nextstep.mvc.tobe;

public class JspViewResolver implements ViewResolver {
    @Override
    public View resolve(Object result) {
        return new JspView((String) result);
    }
}
