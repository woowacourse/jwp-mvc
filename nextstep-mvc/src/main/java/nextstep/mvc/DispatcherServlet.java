package nextstep.mvc;

import nextstep.mvc.tobe.Adapter;
import nextstep.mvc.tobe.AdapterManager;
import nextstep.mvc.tobe.ModelAndView;
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

    private List<HandlerMapping> handlerMappings;
    private AdapterManager adapterManager;

    public DispatcherServlet(List<HandlerMapping> handlerMappings, List<Adapter> adapters) {
        this.handlerMappings = handlerMappings;
        this.adapterManager = new AdapterManager(adapters);
    }

    @Override
    public void init() {
        for (HandlerMapping handlerMapping : handlerMappings) {
            handlerMapping.initialize();
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        Object selectedHandler = findHandler(req);
        try {
            Adapter handler = adapterManager.getHandlerAdapter(selectedHandler);
            ModelAndView mav = handler.handle(selectedHandler, req, resp);
            mav.getView().render(mav.getModel(), req, resp);
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }

    private Object findHandler(HttpServletRequest request) {
        for (HandlerMapping handlerMapping : handlerMappings) {
            Object execution = handlerMapping.getHandler(request);
            if (Objects.nonNull(execution)) {
                return execution;
            }
        }

        throw new IllegalArgumentException();
    }
}
