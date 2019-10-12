package nextstep.mvc.tobe.view;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private Object view;
    private Map<String, Object> model = new HashMap<>();

    public ModelAndView() {
    }

    public ModelAndView(String view) {
        this.view = view;
    }

    public ModelAndView(View view) {
        this.view = view;
    }

    public ModelAndView(Map<String, Object> model) {
        this.model = model;
    }

    public ModelAndView(Object view, Map<String, Object> model) {
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

    public boolean isViewClass() {
        return view instanceof View;
    }

    public Map<String, Object> getModel() {
        return Collections.unmodifiableMap(model);
    }

    public Object getView() {
        return view;
    }
}
