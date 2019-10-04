package nextstep.mvc;

import nextstep.mvc.tobe.Handler;
import nextstep.mvc.tobe.ModelAndView;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private List<HandlerMapping> mappings;

    public DispatcherServlet(HandlerMapping... mappings) {
        this.mappings = Arrays.asList(mappings);
    }

    @Override
    public void init() {
        mappings.forEach(HandlerMapping::initialize);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        ViewResolver viewResolver = new ViewResolver();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), req.getRequestURI());

        try {
            Handler handler = getHandler(req);
            Object view = handler.execute(req, resp);
            ModelAndView mav = viewResolver.resolve(view);
            mav.render(req, resp);
        } catch (Throwable e) {
            logger.error("Exception: {}", ExceptionUtils.getStackTrace(e));
            throw new ServletException(e);
        }
    }

    private Handler getHandler(HttpServletRequest req) {
        return mappings.stream()
                .map(m -> m.getHandler(req))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new NotFoundHandlerException(req.getRequestURI()));
    }
}
