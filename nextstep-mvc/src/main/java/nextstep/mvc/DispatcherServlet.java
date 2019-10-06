package nextstep.mvc;

import nextstep.mvc.tobe.exception.RequestUrlNotFoundException;
import nextstep.mvc.tobe.handlermapping.HandlerMapping;
import nextstep.mvc.tobe.handlermapping.annotationmapping.HandlerExecution;
import nextstep.mvc.tobe.view.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private HandlerMapping[] handlerMappings;

    public DispatcherServlet(HandlerMapping... handlerMappings) {
        this.handlerMappings = handlerMappings;
    }

    @Override
    public void init() {
        for (HandlerMapping handlerMapping : handlerMappings) {
            handlerMapping.initialize();
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        HandlerExecution handlerExecution = findHandlerExecution(req);

        try {
            ModelAndView modelAndView = handlerExecution.handle(req, resp);
            modelAndView.render(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private HandlerExecution findHandlerExecution(HttpServletRequest req) {
        return Arrays.stream(handlerMappings)
                .map(handlerMapping -> handlerMapping.getHandler(req))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(RequestUrlNotFoundException::new);
    }
}
