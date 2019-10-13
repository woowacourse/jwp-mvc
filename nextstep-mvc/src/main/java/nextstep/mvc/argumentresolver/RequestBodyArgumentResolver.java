package nextstep.mvc.argumentresolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.exception.ObjectMapperException;
import nextstep.web.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestBodyArgumentResolver implements ArgumentResolver {
    @Override
    public boolean canResolve(MethodParameter methodParameter) {
        return methodParameter.isAnnotationPresent(RequestBody.class);
    }

    @Override
    public Object resolve(HttpServletRequest request, HttpServletResponse response, MethodParameter methodParameter) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(request.getInputStream(), methodParameter.getType());
        } catch (Exception e) {
            throw new ObjectMapperException();
        }
    }
}
