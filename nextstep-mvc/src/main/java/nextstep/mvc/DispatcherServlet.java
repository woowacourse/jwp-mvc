package nextstep.mvc;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.handler.HandlerAdapter;
import nextstep.mvc.tobe.handler.HandlerAdapterManager;
import nextstep.mvc.tobe.handler.HandlerMappingManager;
import nextstep.mvc.tobe.view.View;
import nextstep.mvc.tobe.view.ViewResolver;
import nextstep.mvc.tobe.view.ViewResolverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    @Override
    public void init() throws ServletException {
        HandlerMappingManager.initialize();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        try {
            Object handler = HandlerMappingManager.getHandlerMapping(req);
            HandlerAdapter handlerAdapter = HandlerAdapterManager.getHandlerAdapter(handler);
            ModelAndView modelAndView = handlerAdapter.handle(req, resp, handler);

            ViewResolver viewResolver = ViewResolverManager.getView(modelAndView);
            View view = viewResolver.resolve(modelAndView.getViewName());
            view.render(modelAndView.getModel(), req, resp);
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }
}
