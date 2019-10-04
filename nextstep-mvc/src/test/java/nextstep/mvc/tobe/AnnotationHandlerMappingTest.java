package nextstep.mvc.tobe;

import nextstep.db.DataBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
    public void post_method_in_requestMapping_annotation() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/method");
        HandlerExecution execution = handlerMapping.getHandler(request);
        Method method = MyController.class.getDeclaredMethod("postMethod", HttpServletRequest.class, HttpServletResponse.class);
        assertThat(execution.getMethod()).isEqualTo(method);
    }

    @Test
    public void empty_method_in_requestMapping_annotation() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/method");
        HandlerExecution execution = handlerMapping.getHandler(request);
        Method method = MyController.class.getDeclaredMethod("emptyMethod", HttpServletRequest.class, HttpServletResponse.class);
        assertThat(execution.getMethod()).isEqualTo(method);
    }
}
