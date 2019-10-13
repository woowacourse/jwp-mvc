package nextstep.mvc.tobe.view;

import nextstep.mvc.tobe.RequestContext;

import java.util.Map;

public interface View {
    void render(Map<String, ?> model, RequestContext requestContext);
}
