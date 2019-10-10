package nextstep.mvc.core;

import nextstep.mvc.view.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestHandlers {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandlers.class);
    private AnnotationHandlerMapping annotationMapping;

    public RequestHandlers(Object... basePackages) {
        this.annotationMapping = new AnnotationHandlerMapping(basePackages);
    }

    public void initialize() {
        annotationMapping.initialize();
    }

    public ModelAndView handle(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        logger.debug("Method : {}, URI : {}", req.getMethod(), req.getRequestURI());
        return annotationMapping.getHandler(req).handle(req, resp);
    }
}
