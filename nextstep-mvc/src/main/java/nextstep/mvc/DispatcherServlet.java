package nextstep.mvc;

import nextstep.mvc.tobe.AnnotationHandlerMapping;
import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.HandlerMapping;
import nextstep.mvc.tobe.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private HandlerMapping manualHandlerMapping;
    private HandlerMapping annotationHandlerMapping;

    private List<HandlerMapping> handlerMappings;

    public DispatcherServlet(HandlerMapping manualHandlerMapping, AnnotationHandlerMapping annotationHandlerMapping) {
        this.manualHandlerMapping = manualHandlerMapping;
        this.annotationHandlerMapping = annotationHandlerMapping;

        handlerMappings = Arrays.asList(manualHandlerMapping, annotationHandlerMapping);
    }

    @Override
    public void init() {
        manualHandlerMapping.initialize();
        annotationHandlerMapping.initialize();

        for (HandlerMapping handlerMapping : handlerMappings) {
            handlerMapping.initialize();
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        HandlerMapping handler = handlerMappings.stream()
                .filter(handlerMapping -> handlerMapping.getHandler(req) != null)
                .findFirst().orElseThrow(NotSupportedHandlerMethod::new);

        HandlerExecution handlerExecution = handler.getHandler(req);
        try {
            ModelAndView mav = handlerExecution.handle(req, resp);
            mav.getView().render(mav.getModel(), req, resp);
        } catch (Exception e) {
            logger.debug("렌더링 실패");
        }
    }

    private void move(String viewName, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            resp.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
            return;
        }

        RequestDispatcher rd = req.getRequestDispatcher(viewName);
        rd.forward(req, resp);
    }


}
