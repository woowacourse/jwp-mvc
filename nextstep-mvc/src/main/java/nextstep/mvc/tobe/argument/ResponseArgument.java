package nextstep.mvc.tobe.argument;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResponseArgument implements Argument {
    @Override
    public Object getArgument(HttpServletRequest request, HttpServletResponse response) {
        return response;
    }
}
