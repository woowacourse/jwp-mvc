package nextstep.mvc.tobe;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class RequestForwardingView implements View {
    private final String forwardingPath;

    private RequestForwardingView(String forwardingPath) {
        this.forwardingPath = forwardingPath;
    }

    public static RequestForwardingView from(String forwardingPath) {
        return new RequestForwardingView(forwardingPath);
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        RequestDispatcher rd = request.getRequestDispatcher(forwardingPath);

        for(String key : model.keySet()) {
            request.setAttribute(key, model.get(key));
        }

        rd.forward(request, response);
    }
}
