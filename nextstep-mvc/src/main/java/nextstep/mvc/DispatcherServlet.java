package nextstep.mvc;

import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.view.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final int STATUS_CODE_FORBIDDEN = 405;
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private final HandlerMapping hm;

    public DispatcherServlet(HandlerMapping hm) {
        this.hm = hm;
    }

    @Override
    public void init() {
        hm.initialize();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), req.getRequestURI());
        try {
            ModelAndView modelAndView = handle(req, resp);
            modelAndView.render(req, resp);
        } catch (Exception e) {
            logger.error("Exception : {}", e.getMessage());
            resp.setStatus(STATUS_CODE_FORBIDDEN);
        }
    }

    private ModelAndView handle(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Object handler = Optional.ofNullable(getHandler(req))
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 핸들러 입니다."));

        return ((HandlerExecution) handler).handle(req, resp);
    }

    private Object getHandler(HttpServletRequest req) {
        Optional.ofNullable(hm.getHandler(req))
                .orElseThrow(() -> new IllegalArgumentException("URL이 없습니다."));
        return hm.getHandler(req);
    }
}
