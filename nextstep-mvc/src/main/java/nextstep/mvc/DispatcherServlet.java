package nextstep.mvc;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.NotFoundHandlerException;
import nextstep.mvc.tobe.View;
import nextstep.mvc.tobe.handlerAdapter.HandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    public static final String DEFAULT_REDIRECT_PREFIX = "redirect:";
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private List<HandlerMapping> handlerMappings;
    private List<HandlerAdapter> handlerAdapters;

    public DispatcherServlet(List<HandlerMapping> handlerMappings, List<HandlerAdapter> handlerAdapters) {
        this.handlerMappings = handlerMappings;
        this.handlerAdapters = handlerAdapters;
    }

    @Override
    public void init() {
        handlerMappings.stream()
                .forEach(HandlerMapping::initialize);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        try {
            Object handler = handlerMappings.stream()
                    .map(handlerMapping -> handlerMapping.getHandler(req))
                    .findAny()
                    .orElseThrow(NotFoundHandlerException::new);

            HandlerAdapter handlerAdapter = handlerAdapters.stream()
                    .filter(adapter -> adapter.canAdapt(handler))
                    .findFirst()
                    .orElseThrow(NotFoundAdapterException::new);

            ModelAndView mv = handlerAdapter.adapt(handler,req,resp);
            move(mv, req, resp);
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }


    }

    private void move(ModelAndView mv, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        View view = mv.getView();
        view.render(mv.getModel(), req, resp);
    }
}
