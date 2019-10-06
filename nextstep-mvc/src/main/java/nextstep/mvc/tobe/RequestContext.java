package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.tobe.view.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class RequestContext {
    private static final String REQUEST = "request";
    private static final String RESPONSE = "response";
    private static final String MODEL = "model";

    private Map<String, Object> attributes = Maps.newHashMap();

    public RequestContext(HttpServletRequest request, HttpServletResponse response) {
        attributes.put(RequestContextKey.REQUEST.getKey(), request);
        attributes.put(RequestContextKey.RESPONSE.getKey(), response);
        attributes.put(RequestContextKey.MODEL.getKey(), new Model());
    }

    public HttpServletRequest getHttpServletRequest() {
        return (HttpServletRequest) attributes.get(RequestContextKey.REQUEST.getKey());
    }

    public HttpServletResponse getHttpServletResponse() {
        return (HttpServletResponse) attributes.get(RequestContextKey.RESPONSE.getKey());
    }

    public void addAttribute(String name, Object attribute) {
        attributes.put(name, attribute);
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }
}
