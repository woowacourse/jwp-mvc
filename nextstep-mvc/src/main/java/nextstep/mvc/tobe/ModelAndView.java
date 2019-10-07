package nextstep.mvc.tobe;

import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.RedirectView;
import nextstep.mvc.tobe.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private View view;
    private Map<String, Object> model = new HashMap<String, Object>();

    public ModelAndView() {
    }

    public ModelAndView(final String viewName) {
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            view = new RedirectView(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
            return;
        }
        view = new JspView(viewName);
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
