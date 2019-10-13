package nextstep.mvc.tobe;

import nextstep.mvc.tobe.view.JsonView;
import nextstep.mvc.tobe.view.View;
import nextstep.mvc.tobe.view.ViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private View view;
    private Map<String, Object> model = new HashMap<String, Object>();

    public ModelAndView() {
        this.view = new JsonView();
    }

    public ModelAndView(final String viewName) {
        this.view = ViewResolver.resolve(viewName);
    }

    public ModelAndView(final View view) {
        this.view = view;
    }

    public ModelAndView addObject(String attributeName, Object attributeValue) {
        model.put(attributeName, attributeValue);
        return this;
    }

    public Object getObject(String attributeName) {
        return model.get(attributeName);
    }

    public void render(HttpServletRequest request, HttpServletResponse response) throws Exception {
        view.render(model, request, response);
    }
}
