package nextstep.mvc.tobe.argumentresolver;

import nextstep.mvc.tobe.argumentresolver.exception.ArgumentResolveFailedException;
import nextstep.utils.JsonUtils;
import nextstep.web.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestBodyArgumentResolver implements ArgumentResolver {
    @Override
    public boolean supports(MethodParameter methodParameter) {
        return methodParameter.isAnnotationPresent(RequestBody.class);
    }

    @Override
    public Object resolve(HttpServletRequest request, HttpServletResponse response, MethodParameter methodParameter) {
        try {
            return JsonUtils.toObject(request.getReader(), methodParameter.getType());
        } catch (IOException e) {
            throw new ArgumentResolveFailedException("RequestBody 파싱에 실패했습니다", e);
        }
    }
}