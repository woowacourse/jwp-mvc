package nextstep.mvc;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.HandlerAdapter;
import nextstep.mvc.tobe.exception.NotFoundHandlerException;
import nextstep.mvc.tobe.view.View;
import nextstep.utils.LoggingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private final List<HandlerMapping> handlerMappings;

    public DispatcherServlet(List<HandlerMapping> handlerMappings) {
        this.handlerMappings = handlerMappings;
    }

    @Override
    public void init() {
        for (HandlerMapping handlerMapping : handlerMappings) {
            handlerMapping.initialize();
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logRequest(request);

        try {
            HandlerAdapter handler = getHandlerOf(request);
            ModelAndView modelAndView = handler.execute(request, response);
            move(modelAndView, request, response);
        } catch (Exception error) {
            LoggingUtils.logStackTrace(logger, error);
            throw new ServletException(error.getMessage());
        }
    }

    private void logRequest(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", request.getMethod(), requestUri);
    }

    private HandlerAdapter getHandlerOf(HttpServletRequest request) {
        return handlerMappings.stream()
                .map(handlerMapping -> handlerMapping.getHandler(request))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(NotFoundHandlerException::new);
    }

    private void move(ModelAndView mv, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        View view = mv.getView();
        view.render(mv.getModel(), req, resp);
    }
}
