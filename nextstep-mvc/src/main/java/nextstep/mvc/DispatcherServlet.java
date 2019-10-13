package nextstep.mvc;

import nextstep.mvc.handleradapter.HandlerAdapter;
import nextstep.mvc.handlermapping.HandlerMapping;
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
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private static final long serialVersionUID = 1L;

    private final HandlerMapping rm;
    private final List<HandlerAdapter> supportedAdapters;


    public DispatcherServlet(HandlerMapping rm, List<HandlerAdapter> supportedAdapters) {
        this.rm = rm;
        this.supportedAdapters = supportedAdapters;
    }

    @Override
    public void init() throws ServletException {
        rm.initialize();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        log.debug("Method : {}, Request URI : {}", request.getMethod(), requestUri);

        final Object handler = getHandler(request);
        log.debug("handler: {}", handler);

        getAdapter(handler).render(request, response, handler);
    }

    private Object getHandler(HttpServletRequest request) {
        return rm.getHandler(request)
                .orElseThrow(() -> BadHttpRequestException.from(request));
    }

    private HandlerAdapter getAdapter(Object handler) {
        return supportedAdapters.stream()
                .filter(adapter -> adapter.supports(handler))
                .findFirst()
                .orElseThrow(() -> NotSupportedHandler.ofHandler(handler));
    }
}
