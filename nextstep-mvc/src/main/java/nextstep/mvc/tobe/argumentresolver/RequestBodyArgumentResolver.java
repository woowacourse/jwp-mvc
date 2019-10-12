package nextstep.mvc.tobe.argumentresolver;

import com.google.common.io.CharStreams;
import nextstep.utils.JsonUtils;
import nextstep.web.annotation.RequestBody;
import nextstep.web.support.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestBodyArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supports(MethodParameter parameter) {
        return parameter.hasAnnotation(RequestBody.class);
    }

    @Override
    public Object resolve(MethodParameter parameter, HttpServletRequest request, HttpServletResponse response) {
        try {
            return JsonUtils.toObject(CharStreams.toString(request.getReader()), parameter.getParameterType());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
