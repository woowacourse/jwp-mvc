package nextstep.mvc;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.exception.HandlerNotFoundException;
import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.JspView;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private List<HandlerMapping> requestMappings;

    public DispatcherServlet(List<HandlerMapping> requestMappings) {
        this.requestMappings = requestMappings;
    }

    @Override
    public void init() throws ServletException {
        requestMappings.forEach(HandlerMapping::initialize);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        try {
            Object handler = getHandler(req);
            ModelAndView modelAndView = handle(handler, req, resp);
            View view = modelAndView.getView();
            view.render(modelAndView.getModel(), req, resp);
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }

    private Object getHandler(HttpServletRequest req) {
        return requestMappings.stream()
                .map(requestMapping -> requestMapping.getHandler(req))
                .filter(Objects::nonNull)
                .findAny()
                .orElseThrow(HandlerNotFoundException::new);
    }

    private ModelAndView handle(Object handler, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (handler instanceof Controller) {
            String viewName = ((Controller) handler).execute(req, resp);
            return new ModelAndView(new JspView(viewName));
        }

        if (handler instanceof HandlerExecution) {
            return ((HandlerExecution) handler).handle(req, resp);
        }

        throw new RuntimeException("HANDLE FAILED");
    }
}
