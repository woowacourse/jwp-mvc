package nextstep.mvc.tobe.argument;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Argument {
    Object getArgument(HttpServletRequest request, HttpServletResponse response);
}
