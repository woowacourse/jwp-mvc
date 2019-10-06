package nextstep.mvc;

import com.google.common.collect.Lists;
import nextstep.HandlerAdapter;
import nextstep.ControllerHandlerAdapter;
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
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private List<HandlerMapping> hms = Lists.newArrayList();

    public DispatcherServlet(HandlerMapping... hm) {
        this.hms.addAll(Arrays.asList(hm));
    }

    @Override
    public void init() {
        hms.forEach(HandlerMapping::initialize);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        try {
            Object handler = getHandler(req);
            move(getModelAndView(req, resp, handler), req, resp);
        } catch (Throwable e) {
            logger.error("Exception : {}", e.getMessage());
            throw new ServletException(e.getMessage());
        }
    }

    private ModelAndView getModelAndView(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        HandlerAdapter handlerAdapter = new ControllerHandlerAdapter();
        if (handlerAdapter.supports(handler)) {
            return handlerAdapter.handle(req, resp, handler);
        }
        return ((HandlerExecution) handler).handle(req, resp);
    }

    private Object getHandler(HttpServletRequest req) {
        return hms.stream()
                .filter(hm -> hm.getHandler(req) != null)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("URL이 없습니다."))
                .getHandler(req);
    }

    private void move(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        modelAndView.renderView(req, resp);
    }
}
