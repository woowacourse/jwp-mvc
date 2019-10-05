package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ModelAndView {
    private View view;
    private Map<String, Object> model = new HashMap<>();

    public ModelAndView() {
    }

    public ModelAndView(View view) {
        this.view = view;
    }

    public void renderView(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Objects.requireNonNull(view);
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
