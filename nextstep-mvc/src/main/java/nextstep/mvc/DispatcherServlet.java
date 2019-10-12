package nextstep.mvc;

import nextstep.mvc.exception.HttpException;
import nextstep.mvc.tobe.JspView;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private List<HandlerMapping> handlerMappings;
    private List<HandlerAdapter> handlerAdapters;

    public DispatcherServlet() {
        handlerMappings = new ArrayList<>();
        handlerAdapters = new ArrayList<>();
    }

    @Override
    public void init() {
        handlerMappings.forEach(HandlerMapping::initialize);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), req.getRequestURI());
        try {
            ModelAndView mav = processRequest(req, resp);
            if (Objects.nonNull(mav.getView())) {
                View view = mav.getView();
                view.render(mav.getModel(), req, resp);
                return;
            }
            View view = new JspView(mav.getViewName());
            view.render(mav.getModel(), req, resp);
        } catch (Exception e) {
            logger.debug("Exception : {}", e.getMessage());
        }
    }

    private ModelAndView processRequest(HttpServletRequest request, HttpServletResponse response) {
        for (HandlerMapping handlerMapping : handlerMappings) {
            Object handler = handlerMapping.getHandler(request);
            ModelAndView mav = doProcess(request, response, handler);

            if (Objects.nonNull(mav)) {
                return mav;
            }
        }
        throw new HttpException(HttpStatus.NOT_FOUND);
    }

    private ModelAndView doProcess(HttpServletRequest req, HttpServletResponse resp, Object handler) {
        for (HandlerAdapter handlerAdapter : handlerAdapters) {
            if (handlerAdapter.supports(handler)) {
                return handlerAdapter.handle(req, resp, handler);
            }
        }
        return null;
    }

    public void addHandlerMapping(HandlerMapping handlerMapping) {
        handlerMappings.add(handlerMapping);
    }

    public void addHandlerAdapter(HandlerAdapter handlerAdapter) {
        handlerAdapters.add(handlerAdapter);
    }
}
