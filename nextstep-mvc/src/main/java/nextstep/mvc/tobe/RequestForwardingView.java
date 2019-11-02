package nextstep.mvc.tobe;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestForwardingView that = (RequestForwardingView) o;
        return Objects.equals(forwardingPath, that.forwardingPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(forwardingPath);
    }
}
