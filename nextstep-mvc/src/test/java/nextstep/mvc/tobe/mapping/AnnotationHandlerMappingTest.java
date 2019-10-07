package nextstep.mvc.tobe.mapping;

import nextstep.db.DataBase;
import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.User;
import nextstep.mvc.tobe.handler.HandlerExecution;
import nextstep.mvc.tobe.mapping.AnnotationHandlerMapping;
import nextstep.web.annotation.RequestMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

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
        HandlerExecution execution = handlerMapping.getHandler(request);
        execution.execute(request, response);

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
        execution.execute(request, response);
    }

    @Test
    void PathVariable_두개인_경우() throws InvocationTargetException, IllegalAccessException {
        // given
        final long id = 1;
        final long userId = 2;
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/users/1/2");

        // when
        final HandlerExecution execution = handlerMapping.getHandler(request);
        final ModelAndView mav = (ModelAndView) execution.execute(1, 2);

        // then
        assertThat(mav.getObject("id")).isEqualTo(id);
        assertThat(mav.getObject("userId")).isEqualTo(userId);
    }

    @Test
    void 여러_RequestMapping_지원_하는지_확인() throws Exception {
        // given
        MockHttpServletRequest get = new MockHttpServletRequest("GET", "/users");
        MockHttpServletRequest options = new MockHttpServletRequest("OPTIONS", "/users");

        // when
        HandlerExecution getHandler = handlerMapping.getHandler(get);
        HandlerExecution optionHandler = handlerMapping.getHandler(options);

        // then
        assertThat(getHandler == optionHandler).isTrue();
    }

    @Test
    @DisplayName("RequsetMapping의 method가 없는 경우 모든 Method 지원")
    void requestMapping_method_가_없는_경우() {
        Stream.of(RequestMethod.values())
                .map(method -> new MockHttpServletRequest(method.name(), "/users/nothing"))
                .map(request -> handlerMapping.getHandler(request))
                .forEach(handler -> assertThat(handler).isNotNull());
    }
}
