package nextstep.mvc.tobe.view;

import nextstep.mvc.View;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private static final String FORMAT_JSON_VIEW = "JsonView";

    private String viewName;
    private View view;
    private Map<String, Object> model = new HashMap<String, Object>();

    public ModelAndView() {
    }

    public ModelAndView(String viewName) {
        this.viewName = viewName;
    }

    public ModelAndView(View view) {
        this.view = view;
    }

    public ModelAndView addObject(String attributeName, Object attributeValue) {
        model.put(attributeName, attributeValue);
        return this;
    }

    public boolean isJsonView() {
        return FORMAT_JSON_VIEW.equals(viewName);
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

    public String getViewName() {
        return viewName;
    }
}
