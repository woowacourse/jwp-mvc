package nextstep.mvc;

import nextstep.mvc.exception.NextstepMvcException;
import nextstep.mvc.handler.Handler;
import nextstep.mvc.handler.HandlerFactory;
import nextstep.mvc.handler.handleradapter.HandlerAdapterWrappers;
import nextstep.mvc.handler.handleradapter.SupportedHandlerFactory;
import nextstep.mvc.handler.handlermapping.HandlerMapping;
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
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private static final long serialVersionUID = 1L;

    private final HandlerFactory handlerFactory;

    private DispatcherServlet(HandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
    }

    public static DispatcherServlet from(HandlerMapping mapping, HandlerAdapterWrappers wrappers) {
        return new DispatcherServlet(SupportedHandlerFactory.from(mapping, wrappers));
    }

    @Override
    public void init() throws ServletException {
        log.debug("init called!");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Handler handler = handlerFactory.create(request);

            handler.render(request, response);
        } catch (Exception e) {
            log.error("error: ", e);
            throw NextstepMvcException.ofException(e);
        }
    }
}

