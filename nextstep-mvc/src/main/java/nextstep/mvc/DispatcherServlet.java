package nextstep.mvc;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.ObjectMapperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private final List<HandlerMapping> handlerMappings;
    private final List<HandlerAdapter> handlerAdapters;


    public DispatcherServlet(List<HandlerMapping> handlerMappings, List<HandlerAdapter> handlerAdapters) {
        this.handlerMappings = handlerMappings;
        this.handlerAdapters = handlerAdapters;
    }

    @Override
    public void init() throws ServletException {
        handlerMappings.forEach(HandlerMapping::initialize);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        Object handler = getHandler(req);

        try {
            ModelAndView modelAndView = handlerAdapters.stream()
                    .filter(handlerAdapter -> handlerAdapter.supports(handler))
                    .findAny()
                    .orElseThrow(ObjectMapperException::new)
                    .handle(req, resp, handler);

            modelAndView.getView().render(modelAndView.getModel(), req, resp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object getHandler(HttpServletRequest req) {
        return handlerMappings.stream()
                .filter(handlerMapping -> handlerMapping.getHandler(req) != null)
                .findAny()
                .orElseThrow(ObjectMapperException::new)
                .getHandler(req);
    }
}
