package nextstep.mvc;

import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.adapter.HandlerAdapter;
import nextstep.mvc.tobe.exception.RequestUrlNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";
    private static final long serialVersionUID = 1L;
    private List<HandlerMapping> handlerMappings;
    private List<HandlerAdapter> handlerAdapters;

    public DispatcherServlet(List<HandlerMapping> handlerMappings, List<HandlerAdapter> handlerAdapters) {
        this.handlerMappings = handlerMappings;
        this.handlerAdapters = handlerAdapters;
    }

    @Override
    public void init() {
        for (HandlerMapping handlerMapping : handlerMappings) {
            handlerMapping.initialize();
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            HandlerMapping handlerMapping = findHandlerMapping(req);

            HandlerAdapter handlerAdapter = findHandlerAdapter(handlerMapping);

            ModelAndView modelAndView = handlerAdapter.handle(handlerMapping, req, resp);
            modelAndView.render(req, resp);
        } catch (Exception e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }

    private HandlerAdapter findHandlerAdapter(HandlerMapping handlerMapping) {
        return handlerAdapters.stream()
                .filter(handlerAdapter -> handlerAdapter.isSupports(handlerMapping))
                .findFirst()
                .orElseThrow(RequestUrlNotFoundException::new);
    }

    public HandlerMapping findHandlerMapping(HttpServletRequest req) {
        return handlerMappings.stream()
                .filter(handlerMapping -> handlerMapping.isSupports(req))
                .findFirst()
                .orElseThrow(RequestUrlNotFoundException::new);
    }
}
