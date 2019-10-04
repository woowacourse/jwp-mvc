package nextstep.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private HandlerMapping[] rm;
    private HandlerAdapter[] ha;

    public DispatcherServlet(HandlerMapping[] rm, HandlerAdapter[] ha) {
        this.rm = rm;
        this.ha = ha;
    }

    @Override
    public void init() throws ServletException {
        Arrays.stream(rm)
                .forEach(HandlerMapping::initialize);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        for (HandlerAdapter adapter : ha) {
            if (adapter.isSupported(req)) {
                try {
                    adapter.execute(req, resp);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        }

        throw new IllegalArgumentException();
    }
}
