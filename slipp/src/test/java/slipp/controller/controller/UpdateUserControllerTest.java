package slipp.controller;

import nextstep.mvc.tobe.adapter.HandlerAdapter;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.RedirectView;
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

class UpdateUserControllerTest extends BaseControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(UpdateUserControllerTest.class);

    @BeforeEach
    void setUp() {
        init();
    }

    @Test
    void userUpdate_success() throws Exception {
        User user = new User("sloth", "password", "sloth", "sloth@sloth.com");
        DataBase.addUser(user);

        MockHttpServletRequest request = new MockHttpServletRequest("PUT", "/users/update");
        request.setSession(new MockHttpSession());
        request.getSession().setAttribute("user", user);
        request.setParameter("userId", "sloth");
        request.setParameter("password", "password");
        request.setParameter("name", "나무늘보");
        request.setParameter("email", "sloth@sloth.com");

        MockHttpServletResponse response = new MockHttpServletResponse();

        Object handler = mappingHandler(request, response);
        HandlerAdapter adapter = mappingAdapter(handler);
        ModelAndView mav = adapter.handle(request, response, handler);

        assertThat(mav.getView()).isEqualTo(new RedirectView("/"));
        assertThat(DataBase.findUserById("sloth").getName()).isEqualTo("나무늘보");
    }

}