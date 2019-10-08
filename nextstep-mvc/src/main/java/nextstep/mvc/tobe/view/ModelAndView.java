package nextstep.mvc.tobe.view;

import nextstep.mvc.tobe.exception.RenderFailedException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private View view;
    private Map<String, Object> model = new HashMap<>();

    public ModelAndView() {
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

    public void render(HttpServletRequest req, HttpServletResponse resp) {
        try {
            view.render(model, req, resp);
        } catch (ServletException | IOException e) {
            throw new RenderFailedException(e);
        }
    }

    public static final class ModelAndViewBuilder {
        private View view;
        private Map<String, Object> model = new HashMap<>();

        private ModelAndViewBuilder(View view) {
            this.view = view;
        }

        public static ModelAndViewBuilder of(View view) {
            return new ModelAndViewBuilder(view);
        }

        public ModelAndViewBuilder append(String key, Object value) {
            model.put(key, value);
            return this;
        }

        public ModelAndView build() {
            ModelAndView modelAndView = new ModelAndView(view);
            modelAndView.model = this.model;
            return modelAndView;
        }
    }
}
