package nextstep.mvc.tobe.argument;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ParameterIntArgument extends ParameterArgument {
    public ParameterIntArgument(String name) {
        super(name);
    }

    @Override
    public Object getArgument(HttpServletRequest request, HttpServletResponse response) {
        return Integer.parseInt((String) super.getArgument(request, response));
    }
}
