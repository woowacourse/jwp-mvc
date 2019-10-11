package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.tobe.view.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class RequestContext {
    private Map<String, Object> attributes = Maps.newHashMap();
    private boolean isHandled = false;

    public RequestContext(HttpServletRequest request, HttpServletResponse response) {
        attributes.put(RequestContextKey.REQUEST.getKey(), request);
        attributes.put(RequestContextKey.RESPONSE.getKey(), response);
        attributes.put(RequestContextKey.MODEL.getKey(), new Model());
        attributes.put(RequestContextKey.HTTP_SESSION.getKey(), request.getSession());
    }

    public HttpServletRequest getHttpServletRequest() {
        return (HttpServletRequest) attributes.get(RequestContextKey.REQUEST.getKey());
    }

    public HttpServletResponse getHttpServletResponse() {
        return (HttpServletResponse) attributes.get(RequestContextKey.RESPONSE.getKey());
    }

    public void addAttribute(String name, Object attribute) {
        attributes.putIfAbsent(name, attribute);
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public boolean isHandled() {
        return isHandled;
    }

    public void completeResponse() {
        isHandled = true;
    }
}
