package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ModelAndViewResolver implements ViewResolver {
    @Override
    public void resolve(HttpServletRequest req, HttpServletResponse resp, Object view) throws Exception {
        ModelAndView modelAndView = (ModelAndView) view;
        modelAndView.getView().render(modelAndView.getModel(), req, resp);
    }
}
