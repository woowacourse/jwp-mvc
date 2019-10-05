package nextstep.mvc;

import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class AnnotationHandlerAdapter implements HandlerAdapter {
    private final HandlerMapping annotationHandlerMapping;

    public AnnotationHandlerAdapter(HandlerMapping annotationHandlerMapping) {
        annotationHandlerMapping.initialize();
        this.annotationHandlerMapping = annotationHandlerMapping;
    }

    @Override
    public boolean isSupported(HttpServletRequest request) {
        return Objects.nonNull(annotationHandlerMapping.getHandler(request));
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HandlerExecution handler = (HandlerExecution) annotationHandlerMapping.getHandler(request);
        return handler.handle(request, response);
    }
}
