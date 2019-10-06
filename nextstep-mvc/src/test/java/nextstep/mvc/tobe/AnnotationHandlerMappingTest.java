package nextstep.mvc.tobe;

import nextstep.db.DataBase;
import nextstep.web.annotation.RequestMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AnnotationHandlerMappingTest {
    private AnnotationHandlerMapping handlerMapping;

    @BeforeEach
    public void setup() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
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
        HandlerExecution execution = handlerMapping.getHandler(request);
        execution.execute(request, response);

        assertThat(request.getAttribute("user")).isEqualTo(user);
    }

    @Test
    @DisplayName("RequestMapping의 RequestMethod를 지정해주면 해당 Http method를 지원한다.")
    public void post_method_in_requestMapping_annotation() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/one-method");
        Method expectedMethod = getControllerMethod(MyController.class, "handleOneRequestMethod");

        HandlerExecution matchedExecution = handlerMapping.getHandler(request);

        assertThat(matchedExecution.getMethod()).isEqualTo(expectedMethod);
    }

    @Test
    @DisplayName("RequestMapping의 RequestMethod를 지정하지 않으면 모든 Http method를 지원한다.")
    public void empty_method_in_requestMapping_annotation() throws Exception {
        // Given
        String requestUrl = "/all-method";
        List<MockHttpServletRequest> requests = getRequestsOfAllRequestMethod(requestUrl);
        Method expectedMethod = getControllerMethod(MyController.class, "handleAllRequestMethods");

        // When
        List<HandlerExecution> executionsOfRequests = new ArrayList<>();
        for (MockHttpServletRequest request : requests) {
            executionsOfRequests.add(handlerMapping.getHandler(request));
        }

        // Then
        for (HandlerExecution execution : executionsOfRequests) {
            assertThat(execution.getMethod()).isEqualTo(expectedMethod);
        }
    }

    @Test
    @DisplayName("같은 url에 대해서라면 RequestMethod가 지정된 handler가 우선시되어 매핑된다.")
    public void name() throws NoSuchMethodException {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/method");
        Method expectedMethod = getControllerMethod(MyController.class, "handlePost");

        HandlerExecution matchedExecution = handlerMapping.getHandler(request);

        assertThat(matchedExecution.getMethod()).isEqualTo(expectedMethod);
    }

    private void createUser(User user) throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/users");
        request.setParameter("userId", user.getUserId());
        request.setParameter("password", user.getPassword());
        request.setParameter("name", user.getName());
        request.setParameter("email", user.getEmail());
        MockHttpServletResponse response = new MockHttpServletResponse();
        HandlerExecution execution = handlerMapping.getHandler(request);
        execution.execute(request, response);
    }

    private Method getControllerMethod(Class controller, String methodName) throws NoSuchMethodException {
        return controller.getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
    }

    private List<MockHttpServletRequest> getRequestsOfAllRequestMethod(String requestUrl) {
        List<MockHttpServletRequest> requests = new ArrayList<>();

        for (RequestMethod requestMethod : RequestMethod.values()) {
            requests.add(new MockHttpServletRequest(requestMethod.name(), requestUrl));
        }
        return requests;
    }
}
