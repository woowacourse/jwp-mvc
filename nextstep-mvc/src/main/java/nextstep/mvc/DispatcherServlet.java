package nextstep.mvc;

import nextstep.mvc.exception.AdapterNotFoundException;
import nextstep.mvc.exception.HandlerNotFoundException;
import nextstep.mvc.tobe.handler.HandlerAdapters;
import nextstep.mvc.tobe.handler.HandlerMappings;
import nextstep.mvc.tobe.view.ModelAndView;
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

    private HandlerMappings handlerMappings;
    private HandlerAdapters handlerAdapters;

    public DispatcherServlet(HandlerMappings handlerMappings, HandlerAdapters handlerAdapters) {
        this.handlerMappings = handlerMappings;
        this.handlerAdapters = handlerAdapters;
    }

    @Override
    public void init() {
        handlerMappings.init();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        try {
            Object handler = findHandler(req);
            ModelAndView modelAndView = handlerAdapters.adapt(handler, req, resp);
            modelAndView.render(req, resp);

        } catch (HandlerNotFoundException | AdapterNotFoundException e) {
            logger.error("Exception : {}", e);
            resp.sendError(404);
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }

    private Object findHandler(HttpServletRequest req) {
        return handlerMappings.findHandler(req);
    }
}
