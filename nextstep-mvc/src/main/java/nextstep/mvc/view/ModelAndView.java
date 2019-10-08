package nextstep.mvc.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";
    private static final String JSP = ".jsp";

    private View view;
    private Map<String, Object> model = new HashMap<>();

    public ModelAndView() {
    }

    public ModelAndView(String viewName) {
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX) || viewName.endsWith(JSP)) {
            this.view = new JspView(viewName);
        }
    }

    public ModelAndView(View view) {
        this.view = view;
    }

    public void render(HttpServletRequest request, HttpServletResponse response) throws Exception {
        view.render(model, request, response);
    }

    public ModelAndView addObject(String attributeName, Object attributeValue) {
        model.put(attributeName, attributeValue);
        return this;
    }

    public Object getObject(String attributeName) {
        return model.get(attributeName);
    }

    public View getView() {
        return view;
    }
}
