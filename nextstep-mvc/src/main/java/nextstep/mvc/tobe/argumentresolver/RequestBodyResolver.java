package nextstep.mvc.tobe.argumentresolver;

import nextstep.mvc.tobe.exception.ArgumentResolveException;
import nextstep.utils.JsonUtils;
import nextstep.web.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Parameter;

public class RequestBodyResolver implements Argument {
    @Override
    public boolean isMapping(Parameter parameter) {
        return parameter.isAnnotationPresent(RequestBody.class);
    }

    @Override
    public Object resolve(HttpServletRequest request, HttpServletResponse response, Parameter parameter) {
        return getBody(request, parameter.getType());
    }

    private static <T> T getBody(HttpServletRequest request, Class<T> returnType) {
        try {
            return JsonUtils.toObject(request.getReader().readLine(), returnType);
        } catch (IOException e) {
            throw new ArgumentResolveException("요청하신 파라미터를 생성할 수 없습니다.");
        }
    }
}
