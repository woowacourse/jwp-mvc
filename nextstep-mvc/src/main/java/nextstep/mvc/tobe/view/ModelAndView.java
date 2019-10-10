package nextstep.mvc.tobe.view;

import nextstep.mvc.tobe.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ModelAndView {
    private static final String REDIRECT_PREFIX = "redirect:";

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

    public static ModelAndView of(String url) {
        if (url.startsWith(REDIRECT_PREFIX)) {
            return redirect(url.substring(REDIRECT_PREFIX.length()));
        }

        return forward(url);
    }

    public static ModelAndView forward(String url) {
        return new ModelAndView(new TemplateView(url));
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

    public void render(HttpServletRequest request, HttpServletResponse response) throws Exception {
        view.render(model, request, response);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelAndView that = (ModelAndView) o;
        return Objects.equals(view, that.view) &&
                Objects.equals(model, that.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(view, model);
    }
}
