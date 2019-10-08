package nextstep.mvc;

import nextstep.mvc.tobe.HandlerAdapterManager;
import nextstep.mvc.tobe.HandlerMappingManager;
import nextstep.mvc.tobe.View;
import nextstep.mvc.tobe.view.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private final HandlerMappingManager handlerMappingManager;
    private final HandlerAdapterManager handlerAdapterManager;

    public DispatcherServlet(List<HandlerMapping> handlerMappings, List<HandlerAdapter> handlerAdapters) {
        handlerMappingManager = new HandlerMappingManager(handlerMappings);
        handlerAdapterManager = new HandlerAdapterManager(handlerAdapters);
    }

    @Override
    public void init() throws ServletException {
        handlerMappingManager.initialize();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        Handler handler = handlerMappingManager.getHandler(req);

        try {
            ModelAndView mav = handlerAdapterManager.handle(handler, req, resp);
            render(mav, req, resp);
        } catch (Exception e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }

    private void render(ModelAndView mav, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        View view = mav.getView();
        Map<String, Object> model = mav.getModel();

        view.render(model, req, resp);
    }
}
