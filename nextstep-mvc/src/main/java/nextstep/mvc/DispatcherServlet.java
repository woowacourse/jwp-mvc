package nextstep.mvc;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.*;
import nextstep.utils.LoggingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private final HandlerMapping handlerMapping;
    private final AnnotationHandlerMapping annotationHandlerMapping;

    public DispatcherServlet(HandlerMapping handlerMapping, AnnotationHandlerMapping annotationHandlerMapping) {
        this.handlerMapping = handlerMapping;
        this.annotationHandlerMapping = annotationHandlerMapping;
    }

    @Override
    public void init() {
        handlerMapping.initialize();
        annotationHandlerMapping.initialize();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ServletRequestHandler handler = getHandlerOf(request);
            ModelAndView modelAndView = handler.execute(request, response);
            move(modelAndView, request, response);
        } catch (Exception error) {
            LoggingUtils.logStackTrace(logger, error);
            throw new ServletException(error.getMessage());
        }
    }

    private ServletRequestHandler getHandlerOf(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", request.getMethod(), requestUri);

        try {
            return annotationHandlerMapping.getHandler(request);
        } catch (NotFoundHandlerException e) {
            Controller controller = handlerMapping.getHandler(requestUri);
            return new ControllerAdapter(controller);
        }
    }

    private void move(ModelAndView mv, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        View view = mv.getView();
        view.render(mv.getModel(), req, resp);
    }
}
