package nextstep.mvc;

import nextstep.mvc.exception.NotFoundHandlerAdapterException;
import nextstep.mvc.exception.NotFoundHandlerException;
import nextstep.mvc.exception.NotFoundViewResolverExcpetion;
import nextstep.mvc.handleradapter.AnnotationHandlerAdapter;
import nextstep.mvc.handleradapter.HandlerAdapter;
import nextstep.mvc.handlermapping.HandlerMapping;
import nextstep.mvc.tobe.view.*;
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
    private List<HandlerAdapter> handlerAdapters;
    private List<ViewResolver> viewResolvers;

    public DispatcherServlet(List<HandlerMapping> handlerMappings) {
        this.handlerMappings = handlerMappings;
    }

    @Override
    public void init() throws ServletException {
        for (HandlerMapping handlerMapping : handlerMappings) {
            handlerMapping.initialize();
        }
        handlerAdapters = Arrays.asList(new AnnotationHandlerAdapter());
        viewResolvers = Arrays.asList(new StringViewResolver(), new DefaultViewResolver());
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
                .orElseThrow(NotFoundViewResolverExcpetion::new);
    }
}
