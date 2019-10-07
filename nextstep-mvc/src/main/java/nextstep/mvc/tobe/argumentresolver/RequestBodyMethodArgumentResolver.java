package nextstep.mvc.tobe.argumentresolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.MethodParameter;
import nextstep.mvc.tobe.RequestContext;
import nextstep.web.annotation.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RequestBodyMethodArgumentResolver implements HandlerMethodArgumentResolver {
    private static final Logger logger = LoggerFactory.getLogger(RequestBodyMethodArgumentResolver.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public boolean supports(MethodParameter methodParameter) {
        return methodParameter.isAnnotatedWith(RequestBody.class);
    }

    @Override
    public Object resolve(RequestContext requestContext, MethodParameter methodParameter) {
        HttpServletRequest request = requestContext.getHttpServletRequest();
        try {
            return OBJECT_MAPPER.readValue(request.getInputStream(), methodParameter.getType());
        } catch (IOException e) {
            logger.error("Http Request Exception : ", e);
            throw new RequestBodyParsingFailedException();
        }
    }
}
