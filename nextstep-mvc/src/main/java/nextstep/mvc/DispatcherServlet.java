package nextstep.mvc;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.AnnotationHandlerMapping;
import nextstep.mvc.tobe.HandlerExecution;
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
import java.util.Optional;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

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

    // @TODO refactoring
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);
        Object handler = getHandlerFromMapping(req);
        try {
            if (handler instanceof Controller) {
                String viewName = ((Controller) handler).execute(req, resp);
                move(viewName, req, resp);
            } else if (handler instanceof HandlerExecution) {
                ModelAndView modelAndView = ((HandlerExecution) handler).handle(req, resp);
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
        return Optional.ofNullable(requestMapping.getHandler(request))
                .orElseGet(() -> annotationHandlerMapping.getHandler(request));
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
