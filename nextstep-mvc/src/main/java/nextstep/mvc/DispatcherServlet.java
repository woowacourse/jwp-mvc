package nextstep.mvc;

import nextstep.mvc.tobe.adapter.HandlerAdapterManager;
import nextstep.mvc.tobe.handler.HandlerMappingManager;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.viewResolver.ViewResolverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private final HandlerMappingManager handlerMappingManager;
    private final HandlerAdapterManager handlerAdapterManager;
    private final ViewResolverManager viewResolverManager;

    public DispatcherServlet(HandlerMappingManager handlerMappingManager, HandlerAdapterManager handlerAdapterManager, ViewResolverManager viewResolverManager) {
        this.handlerMappingManager = handlerMappingManager;
        this.handlerAdapterManager = handlerAdapterManager;
        this.viewResolverManager = viewResolverManager;
    }

    @Override
    public void init() {
        handlerMappingManager.initialize();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            Object handler = handlerMappingManager.findHandler(req);
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
}
