package nextstep.mvc.tobe.view;

import nextstep.mvc.tobe.ModelAndView;

import javax.servlet.http.HttpServletResponse;

public interface ViewResolver {
    boolean support(String viewName);

    View resolve(String viewName);
}
