package nextstep.mvc.tobe.argument;

import nextstep.mvc.tobe.HttpUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ObjectArgument implements Argument {
    private final Class type;

    public ObjectArgument(Class type) {
        this.type = type;
    }

    @Override
    public Object getArgument(HttpServletRequest request, HttpServletResponse response) {
        String body = HttpUtils.generateRequestBody(request);
        return HttpUtils.convertJsonToObject(body, type);
    }
}
