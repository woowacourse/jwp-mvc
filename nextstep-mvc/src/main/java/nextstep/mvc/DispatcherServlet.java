package nextstep.mvc;

import javassist.NotFoundException;
import nextstep.mvc.tobe.HandlerResolver;
import nextstep.mvc.tobe.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private final List<HandlerMapping> handlerMappings;
    private Map<Class, ViewResolver> viewResolvers;

    public DispatcherServlet(List<HandlerMapping> handlerMappings, Map<Class, ViewResolver> viewResolvers) {
        this.handlerMappings = handlerMappings;
        this.viewResolvers = viewResolvers;
    }

    @Override
    public void init() {
        handlerMappings.forEach(HandlerMapping::initialize);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            HandlerMapping handler = getHandler(req);
            HandlerResolver handlerExecution = (HandlerResolver) handler.getHandler(req);
            Object view = handlerExecution.execute(req, resp);
            viewResolvers.get(view.getClass()).resolve(req, resp, view);
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }

    private HandlerMapping getHandler(HttpServletRequest req) throws NotFoundException {
        return handlerMappings.stream()
                .filter(handlerMapping -> handlerMapping.supports(req))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("해당하는 컨트롤러를 찾을 수 없습니다."));
    }
}
