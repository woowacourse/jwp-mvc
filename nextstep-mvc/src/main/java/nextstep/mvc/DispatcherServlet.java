package nextstep.mvc;

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

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private HandlerAdapter[] handlerAdapters;

    public DispatcherServlet(HandlerAdapter[] handlerAdapters) {
        this.handlerAdapters = handlerAdapters;
    }

    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), req.getRequestURI());

        ModelAndView modelAndView = getModelAndView(req, resp);
        modelAndView.render(req, resp);
    }

    private ModelAndView getModelAndView(HttpServletRequest req, HttpServletResponse resp) {
        for (HandlerAdapter adapter : handlerAdapters) {
            if (adapter.isSupported(req)) {
                try {
                    return adapter.execute(req, resp);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    throw new DispatcherServletException();
                }
            }
        }

        return new ModelAndView(new JspView("/404.jsp"));
    }
}
