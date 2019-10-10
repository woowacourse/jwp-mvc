package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebRequestContext implements WebRequest {
    private final HttpServletRequest req;
    private final HttpServletResponse resp;

    public WebRequestContext(final HttpServletRequest req, final HttpServletResponse resp) {
        this.req = req;
        this.resp = resp;
    }

    @Override
    public HttpServletRequest getRequest() {
        return req;
    }

    @Override
    public HttpServletResponse getResponse() {
        return resp;
    }

    @Override
    public String getRequestURI() {
        return req.getRequestURI();
    }
}
