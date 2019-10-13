package nextstep.mvc;

import nextstep.mvc.exception.NotFoundHandlerAdapterException;
import nextstep.mvc.exception.NotFoundHandlerException;
import nextstep.mvc.exception.NotFoundViewResolverExcepetion;
import nextstep.mvc.handleradapter.HandlerAdapter;
import nextstep.mvc.handlermapping.HandlerMapping;
import nextstep.mvc.view.ModelAndView;
import nextstep.mvc.view.View;
import nextstep.mvc.viewresolver.ViewResolver;
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
    private List<HandlerAdapter> handlerAdapters;
    private List<ViewResolver> viewResolvers;

    public DispatcherServlet(List<HandlerMapping> handlerMappings, List<HandlerAdapter> handlerAdapters, List<ViewResolver> viewResolvers) {
        this.handlerMappings = handlerMappings;
        this.handlerAdapters = handlerAdapters;
        this.viewResolvers = viewResolvers;
    }

    @Override
    public void init() throws ServletException {
        handlerMappings
                .forEach(HandlerMapping::initialize);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), req.getRequestURI());

        try {

            Object handler = getHandler(req);
            HandlerAdapter handlerAdapter = getHandlerAdapter(handler);
            ModelAndView modelAndView = handlerAdapter.handle(req, resp, handler);
            ViewResolver viewResolver = getViewResolver(modelAndView);
            View view = viewResolver.resolveView(modelAndView);
            view.render(modelAndView.getModel(), req, resp);

        } catch (Exception e) {

            logger.error(e.getMessage());
            resp.sendError(404);

        }
    }

    private Object getHandler(HttpServletRequest req) {
        return handlerMappings.stream()
                .filter(handlerMapping -> handlerMapping.canHandle(req))
                .findAny()
                .orElseThrow(NotFoundHandlerException::new)
                .getHandler(req);
    }

    private HandlerAdapter getHandlerAdapter(Object handler) {
        return handlerAdapters.stream()
                .filter(adapter -> adapter.canHandle(handler))
                .findAny()
                .orElseThrow(NotFoundHandlerAdapterException::new);
    }

    private ViewResolver getViewResolver(ModelAndView modelAndView) {
        return viewResolvers.stream()
                .filter(viewResolver -> viewResolver.canHandle(modelAndView))
                .findAny()
                .orElseThrow(NotFoundViewResolverExcepetion::new);
    }
}
