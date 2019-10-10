package nextstep.mvc.tobe;

import nextstep.db.DataBase;
import nextstep.web.annotation.RequestMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AnnotationHandlerMappingTest {
    private AnnotationHandlerMapping handlerMapping;

    @BeforeEach
    public void setup() {
        handlerMapping = new AnnotationHandlerMapping("nextstep.mvc.tobe");
        handlerMapping.initialize();
    }

    @Test
    public void create_find() throws Exception {
        User user = new User("pobi", "password", "포비", "pobi@nextstep.camp");
        createUser(user);
        assertThat(DataBase.findUserById(user.getUserId())).isEqualTo(user);

        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/users");
        request.setParameter("userId", user.getUserId());
        MockHttpServletResponse response = new MockHttpServletResponse();
        HandlerAdapter execution = handlerMapping.getHandler(request);
        execution.execute(request, response);

        assertThat(request.getAttribute("user")).isEqualTo(user);
    }

    @Test
    @DisplayName("RequestMapping의 RequestMethod를 지정해주면 해당 Http method를 지원한다.")
    public void post_method_in_requestMapping_annotation() {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/one-method");

        HandlerAdapter matchedExecution = handlerMapping.getHandler(request);

        assertThat(matchedExecution).isNotNull();
    }

    @Test
    @DisplayName("RequestMapping의 RequestMethod를 지정하지 않으면 모든 Http method를 지원한다.")
    public void empty_method_in_requestMapping_annotation() {
        // Given
        String requestUrl = "/all-method";
        List<MockHttpServletRequest> requests = getRequestsOfAllRequestMethod(requestUrl);

        MockHttpServletRequest getRequest = new MockHttpServletRequest("GET", requestUrl);
        HandlerAdapter getRequestHandler = handlerMapping.getHandler(getRequest);

        // When
        for (MockHttpServletRequest request : requests) {
            HandlerAdapter handler = handlerMapping.getHandler(request);

            assertThat(handler).isNotNull();
            assertThat(handler == getRequestHandler).isTrue();
        }
    }

    private void createUser(User user) throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/users");
        request.setParameter("userId", user.getUserId());
        request.setParameter("password", user.getPassword());
        request.setParameter("name", user.getName());
        request.setParameter("email", user.getEmail());
        MockHttpServletResponse response = new MockHttpServletResponse();
        HandlerAdapter execution = handlerMapping.getHandler(request);
        execution.execute(request, response);
    }

    private List<MockHttpServletRequest> getRequestsOfAllRequestMethod(String requestUrl) {
        List<MockHttpServletRequest> requests = new ArrayList<>();

        for (RequestMethod requestMethod : RequestMethod.values()) {
            requests.add(new MockHttpServletRequest(requestMethod.name(), requestUrl));
        }
        return requests;
    }
}
