package nextstep.mvc;

import com.google.common.collect.Lists;
import nextstep.HandlerAdapter;
import nextstep.exception.PageNotFoundException;
import nextstep.mvc.tobe.ErrorView;
import nextstep.mvc.tobe.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final int PAGE_NOT_FOUND = 404;
    private static final int METHOD_NOT_ALLOWED = 405;
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private List<HandlerMapping> hms = Lists.newArrayList();
    private List<HandlerAdapter> adapters = Lists.newArrayList();

    public DispatcherServlet(HandlerMapping... hm) {
        this.hms.addAll(Arrays.asList(hm));
    }

    @Override
    public void init() {
        hms.forEach(handlerMapping -> {
            handlerMapping.initialize();
            adapters.add(handlerMapping.getHandlerAdapter());
        });
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        try {
            Object handler = getHandler(req);
            move(getModelAndView(req, resp, handler), req, resp);
        } catch (PageNotFoundException e) {
            move404Page(req, resp);
        } catch (Throwable e) {
            logger.error("Exception : {}", e.getMessage());
            throw new ServletException(e.getMessage());
        }
    }

    private void move404Page(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            move(new ModelAndView(new ErrorView(PAGE_NOT_FOUND)), req, resp);
        } catch (Throwable e) {
            logger.error("Exception : {}", e.getMessage());
            throw new ServletException(e.getMessage());
        }
    }

    private ModelAndView getModelAndView(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        return adapters.stream()
                .filter(handlerAdapter -> handlerAdapter.supports(handler))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("해당하는 어댑터가 없습니다."))
                .handle(req, resp, handler);
    }

    private Object getHandler(HttpServletRequest req) throws Exception {
        return getHandlerMapping(req).getHandler(req);
    }

    private HandlerMapping getHandlerMapping(HttpServletRequest req) throws Exception {
        return hms.stream()
                .filter(hm -> hm.getHandler(req) != null)
                .findFirst().orElseThrow(PageNotFoundException::new);
    }

    private void move(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        modelAndView.renderView(req, resp);
    }
}
