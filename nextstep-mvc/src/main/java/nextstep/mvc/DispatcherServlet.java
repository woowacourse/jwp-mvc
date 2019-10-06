package nextstep.mvc;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.handler.AnnotationHandler;
import nextstep.mvc.handler.HandlerAdapter;
import nextstep.mvc.handler.LegacyHandler;
import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.view.ModelAndView;
import nextstep.mvc.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final int STATUS_CODE_FORBIDDEN = 405;
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private final List<HandlerMapping> hms;
    private final List<HandlerAdapter> adapters;

    public DispatcherServlet(HandlerMapping... hm) {
        this.hms = Arrays.asList(hm);
        this.adapters = Arrays.asList(
                new LegacyHandler(Controller.class),
                new AnnotationHandler(HandlerExecution.class)
        );
    }

    @Override
    public void init() {
        hms.forEach(HandlerMapping::initialize);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), req.getRequestURI());
        try {
            ModelAndView modelAndView = handle(req, resp);
            View view = modelAndView.getView();
            view.render(modelAndView.getModel(), req, resp);
        } catch (Exception e) {
            logger.error("Exception : {}", e.getMessage());
            resp.setStatus(STATUS_CODE_FORBIDDEN);
        }
    }

    private ModelAndView handle(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Object handler = getHandler(req);
        return adapters.stream()
                .filter(adapter -> adapter.apply(handler))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 형식입니다."))
                .handle(req, resp, handler);
    }

    private Object getHandler(HttpServletRequest req) {
        return hms.stream()
                .filter(hm -> hm.getHandler(req) != null)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("URL이 없습니다."))
                .getHandler(req);
    }
}
