package nextstep.mvc;

import nextstep.mvc.tobe.support.AnnotationApplicationContext;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
    void initialize(AnnotationApplicationContext context);

    Object getHandler(HttpServletRequest request);
}
