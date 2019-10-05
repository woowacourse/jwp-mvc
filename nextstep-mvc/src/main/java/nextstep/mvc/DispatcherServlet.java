package nextstep.mvc;

import nextstep.mvc.asis.Execution;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private List<HandlerMapping> handlerMappings;

    public DispatcherServlet(List<HandlerMapping> handlerMappings) {
        this.handlerMappings = handlerMappings;
    }

    @Override
    public void init() {
        handlerMappings.forEach(HandlerMapping::initialize);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        try {
            renderView(req, resp);
        } catch (Exception e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }

    private void renderView(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Execution execution = getHandler(request);
        if (execution == null) {
            new JspView("/err/404.jsp").render(null, request, response);
            return;
        }
        Object result = execution.execute(request, response);
        if (result instanceof String) {
            ViewResolver.resolve((String) result).render(null, request, response);
            return;
        }
        if (result instanceof ModelAndView) {
            ModelAndView modelAndView = (ModelAndView) result;
            Map<String, Object> model = modelAndView.getModel();
            View view = modelAndView.getView();

            view.render(model, request, response);
            return;
        }
        throw new SecurityException("처리할 수 없는 형식입니다.");
    }

    private Execution getHandler(HttpServletRequest request) {
        return handlerMappings.stream()
                .map(handlerMapping -> handlerMapping.getHandler(request))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    private void move(String viewName, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher(viewName);
        rd.forward(req, resp);
    }
}
