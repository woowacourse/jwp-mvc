package nextstep.mvc.tobe.view;

import javax.servlet.http.HttpServletResponse;

public interface ViewResolver {
    View resolve(HttpServletResponse response);
}
