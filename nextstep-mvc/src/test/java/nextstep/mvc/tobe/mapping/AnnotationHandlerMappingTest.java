package nextstep.mvc.tobe.mapping;

import nextstep.db.DataBase;
import nextstep.mvc.tobe.User;
import nextstep.mvc.tobe.adapter.HandlerAdapter;
import nextstep.mvc.tobe.adapter.HandlerExecutionHandlerAdapter;
import nextstep.mvc.tobe.exception.MappingException;
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
        handlerMapping = new AnnotationHandlerMapping("nextstep.mvc.tobe.MyController");
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
        Object handler = handlerMapping.getHandler(request);
        HandlerAdapter adapter = new HandlerExecutionHandlerAdapter();
        adapter.handle(request, response, handler);

        assertThat(request.getAttribute("user")).isEqualTo(user);
    }

    @Test
    public void create_find_with_PathVariable() throws Exception {
        User user = new User("iva", "password", "이바", "pobi@nextstep.camp");
        createUser(user);
        assertThat(DataBase.findUserById(user.getUserId())).isEqualTo(user);

        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/users/" + user.getUserId());
        MockHttpServletResponse response = new MockHttpServletResponse();
        Object handler = handlerMapping.getHandler(request);
        HandlerAdapter adapter = new HandlerExecutionHandlerAdapter();
        adapter.handle(request, response, handler);

        assertThat(request.getAttribute("user")).isEqualTo(user);
    }

    @Test
    void duplicated_mapping_exception() {
        handlerMapping = new AnnotationHandlerMapping("nextstep.mvc.tobe.ExceptionController");
        assertThrows(MappingException.class, () -> handlerMapping.initialize());
    }

    private void createUser(User user) throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/users");
        request.setParameter("userId", user.getUserId());
        request.setParameter("password", user.getPassword());
        request.setParameter("name", user.getName());
        request.setParameter("email", user.getEmail());
        MockHttpServletResponse response = new MockHttpServletResponse();
        Object handler = handlerMapping.getHandler(request);
        HandlerAdapter adapter = new HandlerExecutionHandlerAdapter();
        adapter.handle(request, response, handler);
    }
}
