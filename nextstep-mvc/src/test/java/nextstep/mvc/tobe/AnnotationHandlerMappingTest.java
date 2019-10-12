package nextstep.mvc.tobe;

import nextstep.db.DataBase;
import nextstep.mvc.tobe.handleradapter.HandlerAdapter;
import nextstep.mvc.tobe.handleradapter.HandlerExecutionHandlerAdapter;
import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.RedirectView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class AnnotationHandlerMappingTest {
    private AnnotationHandlerMapping handlerMapping;

    @BeforeEach
    void setup() {
        handlerMapping = new AnnotationHandlerMapping("nextstep.mvc.tobe");
        handlerMapping.initialize();
    }

    @Test
    void create_find() throws Exception {
        User user = new User("pobi", "password", "포비", "pobi@nextstep.camp");
        createUser(user);
        assertThat(DataBase.findUserById(user.getUserId())).isEqualTo(user);

        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/users");
        request.setParameter("userId", user.getUserId());
        MockHttpServletResponse response = new MockHttpServletResponse();

        HandlerExecution handler = handlerMapping.getHandler(request);
        HandlerAdapter handlerAdapter = new HandlerExecutionHandlerAdapter();

        assertThat(handlerAdapter.supports(handler)).isTrue();
        assertThat(handlerAdapter.handle(request, response, handler).getView()).isEqualTo(new JspView("/user/form"));
        assertThat(request.getAttribute("user")).isEqualTo(user);
    }

    private void createUser(User user) throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/users");
        request.setParameter("userId", user.getUserId());
        request.setParameter("password", user.getPassword());
        request.setParameter("name", user.getName());
        request.setParameter("email", user.getEmail());
        MockHttpServletResponse response = new MockHttpServletResponse();

        HandlerExecution handler = handlerMapping.getHandler(request);
        HandlerAdapter handlerAdapter = new HandlerExecutionHandlerAdapter();

        assertThat(handlerAdapter.supports(handler)).isTrue();
        assertThat(handlerAdapter.handle(request, response, handler).getView()).isEqualTo(new RedirectView("/"));
    }
}
