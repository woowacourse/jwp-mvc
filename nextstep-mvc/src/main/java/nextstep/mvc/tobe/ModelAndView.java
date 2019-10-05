package nextstep.mvc.tobe;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private Object view;
    private Map<String, Object> model = new HashMap<>();

    public ModelAndView() {
    }

    public ModelAndView(final View view) {
        this.view = view;
    }

    public ModelAndView(final String viewName) {
        this.view = viewName;
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
        return view instanceof View ? (View) view : null;
    }

    public String getViewName() {
        return view instanceof String ? (String) view : null;
    }
}