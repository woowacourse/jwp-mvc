package nextstep.mvc;

import nextstep.mvc.tobe.Handler;
import nextstep.mvc.tobe.exception.NotFoundAdapterException;
import nextstep.mvc.tobe.exception.NotFoundHandlerException;
import nextstep.mvc.tobe.exception.NotFoundViewException;
import nextstep.mvc.tobe.handleradapter.HandlerAdapter;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.View;
import nextstep.mvc.tobe.viewresolver.ViewResolver;
import org.apache.commons.lang3.exception.ExceptionUtils;
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

    private List<HandlerMapping> mappings;
    private List<HandlerAdapter> handlerAdapters;
    private List<ViewResolver> viewResolvers;

    public DispatcherServlet(List<HandlerMapping> mappings,
                             List<HandlerAdapter> handlerAdapters,
                             List<ViewResolver> viewResolvers) {
        this.mappings = mappings;
        this.handlerAdapters = handlerAdapters;
        this.viewResolvers = viewResolvers;
    }

    @Override
    public void init() {
        mappings.forEach(HandlerMapping::initialize);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), req.getRequestURI());

        try {
            Handler handler = getHandler(req);
            HandlerAdapter handlerAdapter = getHandlerAdapter(handler);
            ModelAndView mav = handlerAdapter.handle(req, resp, handler);
            View view = getView(mav);
            view.render(mav.getModel(), req, resp);
        } catch (Throwable e) {
            logger.error("Exception: {}", ExceptionUtils.getStackTrace(e));
            throw new ServletException(e);
        }
    }

    private Handler getHandler(HttpServletRequest req) {
        return mappings.stream()
                .map(m -> m.getHandler(req))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new NotFoundHandlerException(req.getRequestURI()));
    }

    private HandlerAdapter getHandlerAdapter(Object handler) {
        return handlerAdapters.stream()
                .filter(adapter -> adapter.supports(handler))
                .findFirst()
                .orElseThrow(NotFoundAdapterException::new);
    }

    private View getView(ModelAndView mav) {
        return viewResolvers.stream()
                .filter(viewResolver -> viewResolver.supports(mav.getViewName()))
                .map(viewResolver -> viewResolver.resolve(mav.getViewName()))
                .findFirst()
                .orElseThrow(NotFoundViewException::new);
    }
}
