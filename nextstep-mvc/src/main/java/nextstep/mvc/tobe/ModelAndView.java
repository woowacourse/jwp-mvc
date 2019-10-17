package nextstep.mvc.tobe;

import nextstep.mvc.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private View view;
    private final Map<String, Object> model = new HashMap<>();

    public ModelAndView() {}

    public ModelAndView(View view) {
        this.view = view;
    }

    public ModelAndView addObject(String attributeName, Object attributeValue) {
        this.model.put(attributeName, attributeValue);
        return this;
    }

    public void render(HttpServletRequest req, HttpServletResponse res) throws Exception {
        this.view.render(this.model, req, res);
    }

    public Object getObject(String attributeName) {
        return this.model.get(attributeName);
    }

    public Map<String, Object> getModel() {
        return Collections.unmodifiableMap(this.model);
    }

    public View getView() {
        return this.view;
    }
}