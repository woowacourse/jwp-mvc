package nextstep.mvc;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.AnnotationHandlerMapping;
import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.ViewAdapter;
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

    private HandlerMapping requestMapping;
    private AnnotationHandlerMapping annotationHandlerMapping;

    public DispatcherServlet(HandlerMapping requestMapping, AnnotationHandlerMapping annotationHandlerMapping) {
        this.requestMapping = requestMapping;
        this.annotationHandlerMapping = annotationHandlerMapping;
    }

    @Override
    public void init() throws ServletException {
        requestMapping.initialize();
        annotationHandlerMapping.initialize();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);
        Object handler = getHandlerFromMapping(req);
        try {
            if (handler instanceof Controller) {
                ModelAndView modelAndView = ViewAdapter.render(((Controller) handler).execute(req, resp));
                modelAndView.render(req, resp);
            } else if (handler instanceof HandlerExecution) {
                ModelAndView modelAndView = ViewAdapter.render(((HandlerExecution) handler).handle(req, resp));
                modelAndView.render(req, resp);
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception : {}", e.getMessage());
            throw new ServletException();
        }
    }

    private Object getHandlerFromMapping(HttpServletRequest request) {
        if (requestMapping.getHandler(request) != null) {
            return requestMapping.getHandler(request);
        }
        return annotationHandlerMapping.getHandler(request);
    }
}
