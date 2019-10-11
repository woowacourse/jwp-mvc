package nextstep.mvc.tobe.argument;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class ParameterArgument implements Argument {
    private final String name;

    public ParameterArgument(String name) {
        this.name = name;
    }

    @Override
    public Object getArgument(HttpServletRequest request, HttpServletResponse response) {
        return request.getParameter(name);
    }
}
