package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;

public class RequestToViewNameTranslator {
    public static String getViewName(HttpServletRequest request) {
        return request.getRequestURI();
    }
}
