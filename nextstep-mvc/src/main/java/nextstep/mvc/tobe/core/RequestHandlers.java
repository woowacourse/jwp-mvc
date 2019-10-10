package nextstep.mvc.tobe.core;

import nextstep.mvc.LegacyHandlerMapping;
import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.RedirectView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestHandlers {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandlers.class);
    private static final String REDIRECT_PREFIX = "redirect:";
    private AnnotationHandlerMapping annotationMapping;
    private LegacyHandlerMapping legacyMapping;

    public RequestHandlers(LegacyHandlerMapping legacyMapping, Object... basePackages) {
        this.annotationMapping = new AnnotationHandlerMapping(basePackages);
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
            return handleLegacy(req, resp, controller);
        }
        return annotationMapping.getHandler(req).handle(req, resp);
    }

    private ModelAndView handleLegacy(HttpServletRequest req, HttpServletResponse resp, Controller controller) throws Exception {
        String viewName = controller.execute(req, resp);
        if (viewName.startsWith(REDIRECT_PREFIX)) {
            return new ModelAndView(new RedirectView(viewName.substring(REDIRECT_PREFIX.length())));
        }
        return new ModelAndView(new JspView(viewName));
    }
}
