package nextstep.mvc;

import nextstep.mvc.exceptions.HandlerAdaptorNotFoundException;
import nextstep.mvc.exceptions.HandlerNotFoundException;
import nextstep.mvc.exceptions.ViewResolverNotFoundException;
import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.View;
import nextstep.mvc.tobe.viewresolver.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final long serialVersionUID = 1L;
    private List<HandlerMapping> handlerMappings;
    private List<HandlerAdaptor> handlerAdaptors;
    private List<ViewResolver> viewResolvers;

    public DispatcherServlet(List<HandlerMapping> handlerMappings, List<HandlerAdaptor> handlerAdaptors, List<ViewResolver> viewResolvers) {
        this.handlerMappings = handlerMappings;
        this.handlerAdaptors = handlerAdaptors;
        this.viewResolvers = viewResolvers;
    }

    @Override
    public void init() {
        handlerMappings.forEach(HandlerMapping::initialize);
    }

    @Override
    protected void service(final HttpServletRequest req, final HttpServletResponse resp) {
        String requestUri = req.getRequestURI();
        log.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        try {
            HandlerExecution handler = (HandlerExecution) findHandler(req);
            HandlerAdaptor handlerAdaptor = findHandlerAdaptor(handler);
            ModelAndView mav = handlerAdaptor.handle(req, resp, handler);
            View view = findView(mav);
            view.render(mav.getModel(), req, resp);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private Object findHandler(final HttpServletRequest req) {
        return handlerMappings.stream().map(handlerMapping -> handlerMapping.getHandler(req))
            .filter(Objects::nonNull)
            .findFirst()
            .orElseThrow(HandlerNotFoundException::new);
    }

    private HandlerAdaptor findHandlerAdaptor(final HandlerExecution handler) {
        return handlerAdaptors.stream().filter(handlerAdaptor -> handlerAdaptor.isSupport(handler))
            .findFirst()
            .orElseThrow(HandlerAdaptorNotFoundException::new);
    }

    private View findView(final ModelAndView mav) {
        return viewResolvers.stream().filter(viewResolver -> viewResolver.isSupport(mav))
            .findFirst()
            .map(ViewResolver::resolve)
            .orElseThrow(ViewResolverNotFoundException::new);
    }
}
