package nextstep.mvc.view;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private Object view;
    private Map<String, Object> model = new HashMap<>();

    public ModelAndView() {
    }

    public ModelAndView(String viewName) {
        this.view = viewName;
    }

    public ModelAndView(View view) {
        this.view = view;
    }

    public ModelAndView addObject(String attributeName, Object attributeValue) {
        model.put(attributeName, attributeValue);
        return this;
    }

    public Object getView() {
        return view;
    }

    public Object getObject(String attributeName) {
        return model.get(attributeName);
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
