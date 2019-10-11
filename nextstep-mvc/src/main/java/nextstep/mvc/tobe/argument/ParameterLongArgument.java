package nextstep.mvc.tobe.argument;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ParameterLongArgument extends ParameterArgument {
    public ParameterLongArgument(String name) {
        super(name);
    }

    @Override
    public Object getArgument(HttpServletRequest request, HttpServletResponse response) {
        return Long.parseLong((String) super.getArgument(request, response));
    }
}
