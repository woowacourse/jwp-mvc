package nextstep.mvc;

import nextstep.mvc.tobe.handler.HandlerExecution;
import nextstep.mvc.tobe.handlerresolver.HandlerResolver;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.View;
import nextstep.mvc.tobe.view.exception.NotSupportedViewException;
import nextstep.mvc.tobe.view.exception.ViewRenderException;
import nextstep.mvc.tobe.view.viewresolver.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final long serialVersionUID = 1L;

    private List<HandlerResolver> handlerResolvers;
    private List<ViewResolver> viewResolvers;

    public DispatcherServlet(List<HandlerResolver> handlerAdapters, List<ViewResolver> viewResolvers) {
        this.handlerResolvers = handlerAdapters;
        this.viewResolvers = viewResolvers;
    }

    @Override
    public void init() throws ServletException {
        handlerResolvers.forEach(HandlerResolver::initialize);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        HandlerExecution handler = mappingHandler(req, resp);
        ModelAndView mav = handle(req, resp, handler);
        View view = resolveView(mav);
        try {
            view.render(mav.getModel(), req, resp);
        } catch (Exception e) {
            logger.debug(e.getMessage(), e.getCause());
            throw new ViewRenderException();
        }
    }

    private HandlerExecution mappingHandler(HttpServletRequest req, HttpServletResponse resp) {
        return handlerResolvers.stream().filter(resolver -> resolver.support(req, resp))
                .findAny()
                .orElseThrow(IllegalAccessError::new)
                .getHandler(req)
                ;
    }

    private ModelAndView handle(HttpServletRequest req, HttpServletResponse resp, HandlerExecution handlerExecution) {
        ModelAndView mav = null;
        try {
            mav = handlerExecution.handle(req, resp);
        } catch (Exception e) {
            logger.debug("message : {}, cause : {}", e.getMessage(), e.getCause());
            throw new RuntimeException(e);
        }
        return mav;
    }

    private View resolveView(ModelAndView mav) {
        return viewResolvers.stream().filter(viewResolver -> viewResolver.support(mav))
                .findFirst()
                .orElseThrow(NotSupportedViewException::new)
                .resolve(mav)
                ;
    }
}
