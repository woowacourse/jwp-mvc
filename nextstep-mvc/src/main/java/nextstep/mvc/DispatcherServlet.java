package nextstep.mvc;

import nextstep.mvc.tobe.AnnotationHandlerMapping;
import nextstep.mvc.tobe.JspView;
import nextstep.mvc.tobe.ModelAndView;
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
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private List<HandlerMapping> handlerMappings;
    private List<HandlerAdapter> handlerAdapters;

    public DispatcherServlet(List<HandlerMapping> handlerMappings, List<HandlerAdapter> handlerAdapters) {
        this.handlerMappings = handlerMappings;
        this.handlerAdapters = handlerAdapters;
    }

    @Override
    public void init() throws ServletException {
        // TODO: 현재는 DispatcherServlet이 생성자로 HandlerMapping과 HandlerAdapter를 DI 받는데, 이가 null일때 디폴트를 처리해보자.
        initDefaultHandlerMappings();
        initDefaultHandlerAdapters();
        for (HandlerMapping handlerMapping : handlerMappings) {
            handlerMapping.initialize();
        }
    }

    private void initDefaultHandlerMappings() {
        handlerMappings.add(new AnnotationHandlerMapping("slipp.controller"));
    }

    private void initDefaultHandlerAdapters() {
        handlerAdapters.add(new AnnotationHandlerAdapter());
    }



    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), req.getRequestURI());

        ModelAndView modelAndView = doHandle(req, resp);
        modelAndView.render(req, resp);
    }

    private ModelAndView doHandle(HttpServletRequest req, HttpServletResponse resp) {
        for (HandlerAdapter adapter : handlerAdapters) {
            Object handler = getHandler(req);
            if (adapter.isSupported(handler)) {
                try {
                    return adapter.handle(req, resp, handler);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    throw new DispatcherServletException();
                }
            }
        }

        return new ModelAndView(new JspView("/404.jsp"));
    }

    private Object getHandler(HttpServletRequest req) {
        for (HandlerMapping handlerMapping : handlerMappings) {
            Object handler = handlerMapping.getHandler(req);
            if (handler != null) {
                return handler;
            }
        }

        throw new NotFoundHandlerException();
    }
}
