package nextstep.mvc.tobe.view;

import java.util.Map;

public class ModelAndView {
    private Object view;
    private Model model = new Model();

    public ModelAndView() {
    }

    public ModelAndView(String viewName) {
        this.view = viewName;
    }

    public ModelAndView(View view) {
        this.view = view;
    }

    public ModelAndView addObject(String attributeName, Object attributeValue) {
        model.addObject(attributeName, attributeValue);
        return this;
    }

    public Object getObject(String attributeName) {
        return model.getObject(attributeName);
    }

    public Map<String, Object> getModelMap() {
        return model.getModelMap();
    }

    public View getView() {
        return view instanceof View ? (View) view : null;
    }

    public boolean hasViewReference() {
        return view instanceof View;
    }

    public String getViewName() {
        return view instanceof String ? (String) view : null;
    }
}
