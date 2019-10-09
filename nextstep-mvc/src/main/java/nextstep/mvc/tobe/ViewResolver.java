package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletResponse;

public interface ViewResolver {
    View resolve(HttpServletResponse response);
}
