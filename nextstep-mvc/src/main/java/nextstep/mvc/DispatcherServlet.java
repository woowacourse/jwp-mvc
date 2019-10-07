package nextstep.mvc;

import nextstep.exception.NotMatchHandlerException;
import nextstep.mvc.tobe.Handler;
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
import java.util.List;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private List<HandlerMapping> handlerMappings;

    public DispatcherServlet(HandlerMapping... handlerMappings) {
        this.handlerMappings = Arrays.asList(handlerMappings);
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
        try {
            Handler handler = getHandlerFromMapping(req);
            ModelAndView modelAndView = handler.handle(req, resp);
            modelAndView.render(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception : {}", e.getMessage());
            throw new ServletException();
        }
    }

    private Handler getHandlerFromMapping(HttpServletRequest request) throws NotMatchHandlerException {
        return handlerMappings.stream()
            .filter(handlerMapping -> handlerMapping.getHandler(request) != null)
            .map(handlerMapping -> handlerMapping.getHandler(request))
            .findAny()
            .orElseThrow(NotMatchHandlerException::new);
    }
}
