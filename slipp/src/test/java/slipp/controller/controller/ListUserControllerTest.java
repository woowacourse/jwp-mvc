package slipp.controller;

import nextstep.mvc.tobe.view.ModelAndView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import slipp.controller.controller.BaseControllerTest;
import slipp.domain.User;
import slipp.support.db.DataBase;

import static org.assertj.core.api.Assertions.assertThat;

class ListUserControllerTest extends BaseControllerTest {
    private static final Logger log = LoggerFactory.getLogger(ListUserControllerTest.class);

    @BeforeEach
    void setUp() {
        init();
    }

    @Test
    void listUser_not_Login_request_fail() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/users");
        MockHttpServletResponse response = new MockHttpServletResponse();

        HandlerExecution2 handler = mappingHandler(request, response);
        ModelAndView modelAndView = handler.handle(request, response);

        assertThat(modelAndView.getViewName()).isEqualTo("redirect:/users/loginForm");
    }

    @Test
    void listUser_success() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/users");
        User user = new User("sloth", "password", "redman", "marx@communism.rus");
        request.setSession(new MockHttpSession());
        request.getSession().setAttribute("user", user);

        MockHttpServletResponse response = new MockHttpServletResponse();

        DataBase.addUser(new User("user1", "password", "user1", "user1@user.com"));
        DataBase.addUser(new User("user2", "password", "user2", "user2@user.com"));
        DataBase.addUser(new User("user3", "password", "user3", "user3@user.com"));

        HandlerExecution2 handler = mappingHandler(request, response);
        handler.handle(request, response);

        assertThat(request.getAttribute(("users"))).isNotNull();
    }
}