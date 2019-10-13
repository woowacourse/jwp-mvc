package nextstep.mvc.tobe.returnvaluehandler;

import nextstep.mvc.tobe.HandlerMethod;
import nextstep.mvc.tobe.RequestContext;
import nextstep.mvc.tobe.ResponseEntity;
import nextstep.utils.JsonUtils;
import nextstep.web.annotation.ResponseBody;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ResponseBodyMethodReturnValueHandler implements HandlerMethodReturnValueHandler {
    @Override
    public boolean supports(HandlerMethod handlerMethod) {
        return handlerMethod.isAnnotatedWith(ResponseBody.class);
    }

    @Override
    public void handle(RequestContext requestContext, Object returnValue) {
        Object body = getBody(requestContext, returnValue);
        HttpServletResponse response = requestContext.getHttpServletResponse();
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        JsonUtils.writeValue(response, body);
        requestContext.completeResponse();
    }

    private Object getBody(RequestContext requestContext, Object returnValue) {
        if (returnValue instanceof ResponseEntity) {
            return handleResponseEntity(requestContext, (ResponseEntity) returnValue);
        }

        return returnValue;
    }

    private Object handleResponseEntity(RequestContext requestContext, ResponseEntity responseEntity) {
        HttpServletResponse response = requestContext.getHttpServletResponse();
        response.setStatus(responseEntity.getHttpStatus().getStatus());

        Map<String, String> headers = responseEntity.getHttpHeaders();
        headers.forEach(response::addHeader);

        return responseEntity.getBody();
    }
}
