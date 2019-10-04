package nextstep.mvc;

import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.ModelAndView;

public class ViewResolver {
    public static ModelAndView resolve(Object object) {
        if (object instanceof String) {
            return new ModelAndView(new JspView((String) object));
        }

        return (ModelAndView) object;
    }
}