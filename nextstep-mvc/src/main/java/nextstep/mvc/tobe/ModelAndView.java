package nextstep.mvc.tobe;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private static final String REDIRECT_PREFIX = "redirect:";

    private View view;
    private Map<String, Object> model = new HashMap<String, Object>();

    public ModelAndView() {
    }

    public ModelAndView(String viewPath) {
        if (viewPath.startsWith(REDIRECT_PREFIX)) {
            this.view = new RedirectView(viewPath.substring(REDIRECT_PREFIX.length()));
            return;
        }
        this.view = new JspView(viewPath);
    }

    public ModelAndView(View view) {
        this.view = view;
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

    public View getView() {
        return view;
    }
}
