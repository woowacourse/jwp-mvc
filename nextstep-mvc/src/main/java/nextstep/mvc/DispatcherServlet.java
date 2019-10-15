package nextstep.mvc;

import nextstep.mvc.exception.NotSupportedViewTypeException;
import nextstep.mvc.view.ModelAndView;
import nextstep.mvc.view.viewresolver.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    public static final String NOT_SUPPORTED_VIEW_TYPE_ERROR = "지원하지 않음";

    private List<HandlerMapping> handlerMappings;
    private List<ViewResolver> viewResolvers;

    public DispatcherServlet(List<HandlerMapping> handlerMappings) {
        this.handlerMappings = handlerMappings;
        this.viewResolvers = Arrays.asList(
                new StringViewResolver(),
                new JsonViewResolver(),
                new JspViewResolver(),
                new RedirectViewResolver(),
                new ModelAndViewResolver()
        );
    }

    @Override
    public void init() throws ServletException {
        handlerMappings.forEach(HandlerMapping::initialize);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);
        try {
            Object handler = getHandler(req);
            Object view =  ((Controller) handler).handle(req, resp);
            ViewResolver viewResolver = getViewResolver(view);
            ModelAndView mav = viewResolver.resolve(view);
            mav.render(req, resp);
        } catch (Exception e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }

    private Object getHandler(HttpServletRequest request) {
        return handlerMappings.stream()
                .map(handlerMapping -> handlerMapping.getHandler(request))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    private ViewResolver getViewResolver(Object view) {
        return viewResolvers.stream()
                .filter(resolver -> resolver.canResolve(view))
                .findFirst()
                .orElseThrow(() -> new NotSupportedViewTypeException(NOT_SUPPORTED_VIEW_TYPE_ERROR));
    }
}
