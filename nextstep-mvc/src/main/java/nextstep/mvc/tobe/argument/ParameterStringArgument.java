package nextstep.mvc.tobe.argument;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ParameterStringArgument extends ParameterArgument {
    public ParameterStringArgument(String name) {
        super(name);
    }

    @Override
    public Object getArgument(HttpServletRequest request, HttpServletResponse response) {
        return super.getArgument(request, response);
    }
}
