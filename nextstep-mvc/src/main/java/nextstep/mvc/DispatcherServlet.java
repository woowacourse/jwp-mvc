package nextstep.mvc;

import nextstep.mvc.tobe.AnnotationHandlerMapping;
import nextstep.mvc.tobe.HandlerAdapter;
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
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), req.getRequestURI());
        Object handler = getHandlerFromMapping(req);
        try {
            HandlerAdapter handlerAdapter = new HandlerAdapter(handler);
            ModelAndView modelAndView = ViewAdapter.render(handlerAdapter.run(req, resp));
            modelAndView.render(req, resp);
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
