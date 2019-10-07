package nextstep.mvc;

import nextstep.mvc.view.TextView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private View view;
    private Map<String, Object> model = new HashMap<>();

    public ModelAndView() {
        view = TextView.emptyView();
    }

    public ModelAndView(View view) {
        this.view = view;
    }

    public void renderView(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        view.render(model, req, resp);
    }

    public ModelAndView addObject(String attributeName, Object attributeValue) {
        model.put(attributeName, attributeValue);
        return this;
    }

    public Object getObject(String attributeName) {
        return model.get(attributeName);
    }
}
