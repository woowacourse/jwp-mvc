package nextstep.mvc.handleradapter;

import javax.servlet.http.HttpServletRequest;

public interface HandlerAdapterFactory {
    Handler create(HttpServletRequest request);
}
