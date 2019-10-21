package nextstep.mvc;

import nextstep.mvc.tobe.adapter.HandlerAdapter;
import nextstep.mvc.tobe.adapter.NoSuchAdapterException;
import nextstep.mvc.tobe.handler.HandlerMapping;
import nextstep.mvc.tobe.handler.NoSuchHandlerException;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.exception.ViewRenderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final long serialVersionUID = 1L;

    private List<HandlerMapping> handlerMappings;
    private List<HandlerAdapter> handlerAdapters;

    public DispatcherServlet(List<HandlerMapping> handlerMappings, List<HandlerAdapter> handlerAdapters) {
        this.handlerMappings = handlerMappings;
        this.handlerAdapters = handlerAdapters;
    }

    @Override
    public void init() {
        handlerMappings.forEach(HandlerMapping::initialize);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        Object handler = mappingHandler(req, resp);
        HandlerAdapter adapter = mappingAdapter(handler);
        ModelAndView mav = handleAdapter(req, resp, handler, adapter);
        try {
            mav.render(req, resp);
        } catch (Exception e) {
            logger.debug(e.getMessage(), e.getCause());
            throw new ViewRenderException();
        }
    }

    private ModelAndView handleAdapter(HttpServletRequest req, HttpServletResponse resp, Object handler, HandlerAdapter adapter) {
        try {
            return adapter.handle(req, resp, handler);
        } catch (Exception e) {
            logger.debug(e.getMessage(), e.getCause());
            throw new NoSuchAdapterException();
        }
    }

    private HandlerAdapter mappingAdapter(Object handler) {
        return handlerAdapters.stream().filter(adapter -> adapter.support(handler))
                .findFirst()
                .orElseThrow(NoSuchAdapterException::new);
    }

    private Object mappingHandler(HttpServletRequest req, HttpServletResponse resp) {
        return handlerMappings.stream().filter(handler -> handler.support(req, resp))
                .findFirst()
                .orElseThrow(NoSuchHandlerException::new)
                .getHandler(req);
    }

}
