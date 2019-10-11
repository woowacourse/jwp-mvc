package nextstep.mvc;

import javassist.NotFoundException;
import nextstep.mvc.tobe.adapter.HandlerAdapterManager;
import nextstep.mvc.tobe.handler.NotFoundHandlerException;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.viewResolver.ViewResolverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private final List<HandlerMapping> handlerMappings;
    private final HandlerAdapterManager handlerAdapterManager;
    private final ViewResolverManager viewResolverManager;

    public DispatcherServlet(List<HandlerMapping> handlerMappings, HandlerAdapterManager handlerAdapterManager, ViewResolverManager viewResolverManager) {
        this.handlerMappings = handlerMappings;
        this.handlerAdapterManager = handlerAdapterManager;
        this.viewResolverManager = viewResolverManager;
    }

    @Override
    public void init() {
        handlerMappings.forEach(HandlerMapping::initialize);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            Object handler = findHandler(req);
            HandlerAdapter handlerAdapter = handlerAdapterManager.findHandlerAdapter(handler);
            ModelAndView mv = handlerAdapter.handle(req, resp, handler);

            ViewResolver viewResolver = viewResolverManager.findViewResolver(mv);
            View view = viewResolver.resolve(mv);
            view.render(mv.getModel(), req, resp);
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }

    private Object findHandler(HttpServletRequest req) throws NotFoundException {
        return handlerMappings.stream()
                .map(handlerMapping -> handlerMapping.getHandler(req))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new NotFoundHandlerException("해당하는 Handler를 찾을 수 없습니다."));
    }
}
