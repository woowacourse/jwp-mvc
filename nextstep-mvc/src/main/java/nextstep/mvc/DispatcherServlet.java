package nextstep.mvc;

import nextstep.exception.NoSuchAdapterException;
import nextstep.exception.NoSuchHandlerException;
import nextstep.exception.NoSuchResolverException;
import nextstep.mvc.adapter.HandlerAdapter;
import nextstep.mvc.resolver.ViewResolver;
import nextstep.mvc.view.ModelAndView;
import nextstep.mvc.view.View;
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
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final long serialVersionUID = 1L;

    private List<HandlerMapping> mappings;
    private List<HandlerAdapter> adapters;
    private List<ViewResolver> resolvers;

    public DispatcherServlet(List<HandlerMapping> mappings, List<HandlerAdapter> adapters, List<ViewResolver> resolvers) {
        this.mappings = mappings;
        this.adapters = adapters;
        this.resolvers = resolvers;
    }

    @Override
    public void init() {
        mappings.forEach(HandlerMapping::initialize);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), req.getRequestURI());
        Object handler = getHandler(req);
        HandlerAdapter handlerAdapter = getHandlerAdapter(handler);
        try {
            ModelAndView mav = handlerAdapter.handle(req, resp, handler);
            View view = getViewResolver(mav).resolveView(mav.getView());
            view.render(mav.getModel(), req, resp);
        } catch (Throwable e) {
            logger.error("Exception : {}", e.getMessage());
            throw new ServletException(e.getMessage());
        }
    }

    private Object getHandler(HttpServletRequest req) {
        return mappings.stream()
                .map(m -> m.getHandler(req))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(NoSuchHandlerException::new);
    }

    private HandlerAdapter getHandlerAdapter(Object handler) {
        return adapters.stream()
                .filter(a -> a.supports(handler))
                .findFirst()
                .orElseThrow(NoSuchAdapterException::new);
    }

    private ViewResolver getViewResolver(ModelAndView mav) {
        return resolvers.stream()
                .filter(r -> r.supports(mav))
                .findFirst()
                .orElseThrow(NoSuchResolverException::new);
    }
}
