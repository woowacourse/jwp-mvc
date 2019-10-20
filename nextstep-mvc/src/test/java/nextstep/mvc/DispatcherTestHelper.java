package nextstep.mvc;

import nextstep.ControllerHandlerAdapter;
import nextstep.HandlerAdapter;
import nextstep.HandlerExecutionAdapter;
import nextstep.mvc.tobe.AnnotationHandlerMapping;
import nextstep.web.annotation.RequestMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DispatcherTestHelper {
    private static final String URL_DELIMITER = "\\?";
    private static final String QUERY_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";

    private DispatcherServlet dispatcher;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    public DispatcherTestHelper() {
        List<HandlerAdapter> adapters = Arrays.asList(new ControllerHandlerAdapter(), new HandlerExecutionAdapter());
        dispatcher = new DispatcherServlet(adapters, new AnnotationHandlerMapping("slipp"));
        dispatcher.init();

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    public DispatcherTestHelper get(String url) {
        request.setRequestURI(getPath(url));
        request.setMethod(RequestMethod.GET.name());
        return this;
    }

    private String getPath(String path) {
        String[] tokens = path.split(URL_DELIMITER);
        if (tokens.length == 2) {
            setQueryString(tokens[1]);
        }
        return tokens[0];
    }

    private void setQueryString(String queryString) {
        for (String keyValue : queryString.split(QUERY_DELIMITER)) {
            String[] tokens = keyValue.split(KEY_VALUE_DELIMITER);
            request.setParameter(tokens[0], tokens[1]);
        }
    }

    public DispatcherTestHelper post(String path, Map<String, String> body) {
        request.setRequestURI(path);
        request.setMethod(RequestMethod.POST.name());
        setBody(body);
        return this;
    }

    private void setBody(Map<String, String> body) {
        body.keySet().forEach(key -> request.setParameter(key, body.get(key)));
    }

    public HttpSession getSession() {
        return request.getSession();
    }

    public DispatcherTestHelper setSession(HttpSession session) {
        request.setSession(session);
        return this;
    }

    public void service() throws ServletException {
        dispatcher.service(request, response);
    }

    public MockHttpServletRequest getRequest() {
        return request;
    }

    public MockHttpServletResponse getResponse() {
        return response;
    }
}
