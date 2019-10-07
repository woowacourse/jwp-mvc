package nextstep.mvc.tobe;

import nextstep.db.DataBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AnnotationHandlerMappingTest {
    private AnnotationHandlerMapping handlerMapping;

    @BeforeEach
    public void setup() {
        ControllerScanner controllerScanner = new ControllerScanner("nextstep.mvc.tobe");
        handlerMapping = new AnnotationHandlerMapping(controllerScanner.scan());
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
        HandlerExecution execution = handlerMapping.getHandler(request);
        execution.handle(request, response);

        assertThat(request.getAttribute("user")).isEqualTo(user);
    }

    private void createUser(User user) throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/users");
        request.setParameter("userId", user.getUserId());
        request.setParameter("password", user.getPassword());
        request.setParameter("name", user.getName());
        request.setParameter("email", user.getEmail());
        MockHttpServletResponse response = new MockHttpServletResponse();
        HandlerExecution execution = handlerMapping.getHandler(request);
        execution.handle(request, response);
    }

    @Test
    void test_no_request_mapping_method_get() throws Exception {
        MockHttpServletRequest getRequest = new MockHttpServletRequest("GET", "/test");
        MockHttpServletResponse response = new MockHttpServletResponse();
        HandlerExecution execution = handlerMapping.getHandler(getRequest);
        execution.handle(getRequest, response);

        assertThat(getRequest.getAttribute("test")).isEqualTo(true);
    }

    @Test
    void test_no_request_mapping_method_post() throws Exception {
        MockHttpServletRequest postRequest = new MockHttpServletRequest("POST", "/test");
        MockHttpServletResponse response = new MockHttpServletResponse();
        HandlerExecution execution = handlerMapping.getHandler(postRequest);
        execution.handle(postRequest, response);

        assertThat(postRequest.getAttribute("test")).isEqualTo(true);
    }

    @Test
    void test_no_request_mapping_attribute_name() throws Exception {
        MockHttpServletRequest postRequest = new MockHttpServletRequest("POST", "/no_name");
        MockHttpServletResponse response = new MockHttpServletResponse();
        HandlerExecution execution = handlerMapping.getHandler(postRequest);
        execution.handle(postRequest, response);

        assertThat(postRequest.getAttribute("test_no_value_name")).isEqualTo(true);
    }

    @Test
    void test_empty_request_mapping_url() {
        MockHttpServletRequest postRequest = new MockHttpServletRequest("POST", "");
        MockHttpServletResponse response = new MockHttpServletResponse();
        HandlerExecution execution = handlerMapping.getHandler(postRequest);

        assertThrows(NullPointerException.class, () -> execution.handle(postRequest, response));
    }
}
