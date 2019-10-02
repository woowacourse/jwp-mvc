package nextstep.mvc;

import nextstep.mvc.exception.NotFoundHandlerException;
import nextstep.mvc.tobe.HandlerExecution;
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

    private List<HandlerMapping> handlerMappings;
    private HandlerAdapter handlerAdapter = new HandlerAdapter();

    public DispatcherServlet(List<HandlerMapping> handlerMappings) {
        this.handlerMappings = handlerMappings;
    }

    @Override
    public void init() throws ServletException {
        for (HandlerMapping handlerMapping : handlerMappings) {
            handlerMapping.initialize();
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), req.getRequestURI());

        HandlerExecution handlerExecution = getHandler(req);
        try {
            Object result = handlerExecution.handle(req, resp);
            handlerAdapter.handle(result, req, resp);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resp.sendError(404);
        }
    }

    private HandlerExecution getHandler(HttpServletRequest req) {
        return handlerMappings.stream()
                .filter(handlerMapping -> handlerMapping.canHandle(req))
                .findAny()
                .orElseThrow(NotFoundHandlerException::new)
                .getHandler(req);
    }
}
