package nextstep.mvc;

import nextstep.mvc.tobe.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private HandlerMapping[] handlerMappings;
    private HandlerAdapter[] handlerAdapters;

    public DispatcherServlet(HandlerMapping[] handlerMappings, HandlerAdapter[] handlerAdapters) {
        this.handlerMappings = handlerMappings;
        this.handlerAdapters = handlerAdapters;
    }

    @Override
    public void init() throws ServletException {
        for (HandlerMapping handlerMapping : handlerMappings) {
            handlerMapping.initialize();
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), req.getRequestURI());


        Object handler = getHandler(req);
        HandlerAdapter handlerAdapter = getHandlerAdapter(handler);
        ModelAndView modelAndView = null;
        try {
            modelAndView = handlerAdapter.execute(handler, req, resp);
        } catch (Exception e) {

        }
        modelAndView.render(req, resp);
    }

    private HandlerAdapter getHandlerAdapter(Object handlerMapping) {
        return Arrays.stream(handlerAdapters)
                .filter(handlerAdapter -> handlerAdapter.isSupported(handlerMapping))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    private Object getHandler(HttpServletRequest request) {
        return Arrays.stream(handlerMappings)
                .filter(handlerMapping -> Objects.nonNull(handlerMapping.getHandler(request)))
                .findAny()
                .orElseThrow(IllegalArgumentException::new)
                .getHandler(request);
    }

}
