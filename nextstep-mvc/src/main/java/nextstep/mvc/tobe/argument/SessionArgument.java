package nextstep.mvc.tobe.argument;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionArgument implements Argument {
    @Override
    public Object getArgument(HttpServletRequest request, HttpServletResponse response) {
        return request.getSession();
    }
}
