package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebRequestContext {
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public WebRequestContext(final HttpServletRequest request, final HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse httpServletResponse() {
        return response;
    }
}
