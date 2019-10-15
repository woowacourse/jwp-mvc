package nextstep.mvc.tobe;

import nextstep.mvc.tobe.view.ViewType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private ViewType viewType;
    private String viewName;
    private Map<String, Object> model = new HashMap<>();

    public ModelAndView() {
    }

    public ModelAndView(ViewType viewType) {
        this(viewType, null);
    }

    public ModelAndView(ViewType viewType, String viewName) {
        this.viewType = viewType;
        this.viewName = viewName;
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

    public ViewType getViewType() {
        return viewType;
    }

    public String getViewName() {
        return viewName;
    }
}
