package nextstep.mvc.tobe.view;

import nextstep.mvc.tobe.View;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private View view;
    private Map<String, Object> model = new HashMap<String, Object>();

    public ModelAndView() {
    }

    private ModelAndView(View view) {
        this.view = view;
    }

    public static ModelAndView of(View view) {
        return new ModelAndView(view);
    }

    public static ModelAndView redirect(String redirectUrl) {
        return new ModelAndView(new RedirectView(redirectUrl));
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
