package nextstep.mvc;

import nextstep.mvc.tobe.exception.RequestUrlNotFoundException;
import nextstep.mvc.tobe.handlermapping.HandlerMapping;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private HandlerMapping[] handlerMappings;

    public DispatcherServlet(HandlerMapping... handlerMappings) {
        this.handlerMappings = handlerMappings;
    }

    @Override
    public void init() {
        for (HandlerMapping handlerMapping : handlerMappings) {
            handlerMapping.initialize();
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        for (HandlerMapping handlerMapping : handlerMappings) {
            if (handlerMapping.handle(req, resp)) {
                return;
            }
        }
        throw new RequestUrlNotFoundException();
    }
}
