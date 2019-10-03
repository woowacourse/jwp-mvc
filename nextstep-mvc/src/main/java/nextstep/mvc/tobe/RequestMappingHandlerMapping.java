package nextstep.mvc.tobe;

import nextstep.mvc.LegacyHandlerMapping;
import nextstep.mvc.asis.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestMappingHandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(RequestMappingHandlerMapping.class);
    private AnnotationHandlerMapping annotationMapping;
    private LegacyHandlerMapping legacyMapping;

    public RequestMappingHandlerMapping(LegacyHandlerMapping legacyMapping) {
        this.annotationMapping = new AnnotationHandlerMapping("slipp.controller");
        this.legacyMapping = legacyMapping;
    }

    public void initialize() {
        annotationMapping.initialize();
        legacyMapping.initialize();
    }

    public ModelAndView handle(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        logger.debug("Method : {}, URI : {}", req.getMethod(), req.getRequestURI());
        Controller controller = legacyMapping.getHandler(req.getRequestURI());
        if (controller != null) {
            return new ModelAndView(new JspView(controller.execute(req, resp)));
        }
        return annotationMapping.getHandler(req).handle(req, resp);
    }
}
