package nextstep.mvc.argumentresolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.exception.ObjectMapperException;
import nextstep.web.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestBodyArgumentResolver implements ArgumentResolver {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean canResolve(MethodParameter methodParameter) {
        return methodParameter.isAnnotationPresent(RequestBody.class);
    }

    @Override
    public Object resolve(HttpServletRequest request, HttpServletResponse response, MethodParameter methodParameter) {
        try {
            return objectMapper.readValue(request.getInputStream(), methodParameter.getType());
        } catch (Exception e) {
            throw new ObjectMapperException();
        }
    }
}
