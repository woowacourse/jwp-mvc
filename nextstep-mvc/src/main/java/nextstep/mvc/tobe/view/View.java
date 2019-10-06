package nextstep.mvc.tobe.view;

import nextstep.mvc.tobe.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface View {
    void render(Map<String, ?> model, RequestContext requestContext) throws Exception;
}
