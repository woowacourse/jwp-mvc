package nextstep.mvc.tobe;

import nextstep.db.DataBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class AnnotationHandlerMappingTest {

    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMappingTest.class);
    private AnnotationHandlerMapping handlerMapping;

    @BeforeEach
    public void setup() throws InvocationTargetException, IllegalAccessException {
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
    void getHandlerExecution_GET() throws NoSuchMethodException {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/users");

        HandlerExecution handlerExecution = handlerMapping.getHandler(request);
        Method method = MyController.class.getDeclaredMethod("findUserId", HttpServletRequest.class, HttpServletResponse.class);
        assertThat(handlerExecution.isSameMethod(method)).isTrue();
    }

    @Test
    void getHandlerExecution_POST() throws NoSuchMethodException {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/users");

        HandlerExecution handlerExecution = handlerMapping.getHandler(request);
        Method method = MyController.class.getDeclaredMethod("save", HttpServletRequest.class, HttpServletResponse.class);
        assertThat(handlerExecution.isSameMethod(method)).isTrue();
    }
}
