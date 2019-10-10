package nextstep.mvc;

import nextstep.mvc.adapter.ControllerAdapter;
import nextstep.mvc.adapter.ExecutionAdapter;
import nextstep.mvc.adapter.HandlerExecutionAdapter;
import nextstep.mvc.view.JspView;
import nextstep.mvc.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final long serialVersionUID = 1L;
    private static final String NOT_FOUND_PAGE = "/err/404.jsp";

    private List<HandlerMapping> handlerMappings;
    private List<ExecutionAdapter> adapters;

    public DispatcherServlet(List<HandlerMapping> handlerMappings) {
        this.handlerMappings = handlerMappings;
        this.adapters = new ArrayList<>();
    }

    @Override
    public void init() {
        handlerMappings.forEach(HandlerMapping::initialize);
        adapters.add(new ControllerAdapter());
        adapters.add(new HandlerExecutionAdapter());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        try {
            ModelAndView modelAndView = handle(req, resp);
            View view = modelAndView.getView();
            view.render(modelAndView.getModel(), req, resp);
        } catch (Exception e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }

    private ModelAndView handle(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Execution execution = findExecution(req);
        if (execution == null) {
            return new ModelAndView(new JspView(NOT_FOUND_PAGE));
        }
        ExecutionAdapter adapter = findExecutionAdapter(execution);
        return adapter.execute(req, resp, execution);
    }

    private Execution findExecution(HttpServletRequest request) {
        return handlerMappings.stream()
                .map(handlerMapping -> handlerMapping.getHandler(request))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    private ExecutionAdapter<?> findExecutionAdapter(Execution execution) {
        return adapters.stream()
                .filter(adapter -> adapter.matchClass(execution))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("처리할 수 없는 형태입니다."));
    }
}
