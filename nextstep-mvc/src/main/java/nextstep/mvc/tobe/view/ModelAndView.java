package nextstep.mvc.tobe.view;

import nextstep.mvc.tobe.exception.NotFoundViewResolverException;
import nextstep.mvc.tobe.viewresolver.ViewResolver;

import java.util.*;

public class ModelAndView {
    private String viewName;
    private View view;
    private Map<String, Object> model = new HashMap<>();

    public ModelAndView() {
    }

    public ModelAndView(String viewName) {
        this.viewName = viewName;
    }

    public ModelAndView(View view) {
        this.view = view;
    }

    public ModelAndView(Map<String, Object> model) {
        this.model = model;
    }

    public ModelAndView(View view, Map<String, Object> model) {
        this.view = view;
        this.model = model;
    }

    public ModelAndView addObject(String attributeName, Object attributeValue) {
        model.put(attributeName, attributeValue);
        return this;
    }

    public Object getObject(String attributeName) {
        return model.get(attributeName);
    }

    public Map<String, Object> getModel() {
        return Collections.unmodifiableMap(model);
    }

    public View getView() {
        return view;
    }

    public View getView(final List<ViewResolver> viewResolvers) {
        return Optional.ofNullable(view)
                .orElseGet(() -> findView(viewResolvers));
    }

    private View findView(final List<ViewResolver> viewResolvers) {
        return viewResolvers.stream()
                .filter(viewResolver -> viewResolver.supports(viewName))
                .findAny()
                .map(viewResolver -> viewResolver.resolve(viewName))
                .orElseThrow(() -> new NotFoundViewResolverException(
                        String.format("viewName: %s, View resolver 를 찾을 수 없습니다.", viewName)
                ));
    }
}
