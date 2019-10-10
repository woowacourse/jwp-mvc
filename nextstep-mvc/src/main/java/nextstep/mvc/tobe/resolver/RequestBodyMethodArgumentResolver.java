package nextstep.mvc.tobe.resolver;

import nextstep.mvc.tobe.WebRequest;
import nextstep.mvc.tobe.exception.RequestBodyMethodArgumentResolverException;
import nextstep.utils.JsonUtils;
import nextstep.web.annotation.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RequestBodyMethodArgumentResolver implements HandlerMethodArgumentResolver {
    private static final Logger logger = LoggerFactory.getLogger(RequestBodyMethodArgumentResolver.class);

    @Override
    public boolean supports(final MethodParameter methodParameter) {
        return methodParameter.isAnnotationPresent(RequestBody.class);
    }

    @Override
    public Object resolveArgument(final WebRequest webRequest, final MethodParameter methodParameter) {
        final HttpServletRequest request = webRequest.getRequest();
        try {
            return JsonUtils.toObject(request.getInputStream(), methodParameter.getType());
        } catch (IOException e) {
            logger.error("IOException: {}", e);
            throw new RequestBodyMethodArgumentResolverException(e.getMessage());
        }
    }
}
