package nextstep.mvc.view;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private Object view;
    private Map<String, Object> model = new HashMap<String, Object>();

    public ModelAndView() {
    }

    public ModelAndView(String viewName) {
        this.view = viewName;
    }

    public ModelAndView(View view) {
        this.view = view;
    }

    public ModelAndView(Map<String, Object> model, View view) {
        this.model = model;
        this.view = view;
    }

    public ModelAndView(Map<String, Object> model, String viewName) {
        this.model = model;
        this.view = viewName;
    }

    public ModelAndView addObject(String attributeName, Object attributeValue) {
        model.put(attributeName, attributeValue);
        return this;
    }

    public boolean isViewInstance() {
        return view instanceof View;
    }

    public boolean isStringInstance() {
        return view instanceof String;
    }

    public Object getObject(String attributeName) {
        return model.get(attributeName);
    }

    public Map<String, Object> getModel() {
        return Collections.unmodifiableMap(model);
    }

    public String getViewName() {
        return view instanceof String ? (String) view : null;
    }

    public Object getView() {
        return view;
    }
}
