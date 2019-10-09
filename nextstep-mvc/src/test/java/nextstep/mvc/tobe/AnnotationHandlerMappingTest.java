package nextstep.mvc.tobe;

import nextstep.db.DataBase;
import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.ModelAndView;
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
    void isSupport_true() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/users");
        assertThat(handlerMapping.isSupport(request)).isTrue();
    }

    @Test
    void isSupport_false() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/nope");
        assertThat(handlerMapping.isSupport(request)).isFalse();
    }

    @Test
    void create_find() throws Exception {
        User user = new User("pobi", "password", "포비", "pobi@nextstep.camp");
        createUser(user);
        assertThat(DataBase.findUserById(user.getUserId())).isEqualTo(user);

        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/users");
        request.setParameter("userId", user.getUserId());
        MockHttpServletResponse response = new MockHttpServletResponse();
        ModelAndView mav = handlerMapping.execute(request, response);

        assertThat(handlerMapping.isSupport(request));
        assertThat(mav.getView()).isEqualTo(new JspView("/user/form"));
        assertThat(request.getAttribute("user")).isEqualTo(user);
    }

    private void createUser(User user) throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/users");
        request.setParameter("userId", user.getUserId());
        request.setParameter("password", user.getPassword());
        request.setParameter("name", user.getName());
        request.setParameter("email", user.getEmail());
        MockHttpServletResponse response = new MockHttpServletResponse();
        ModelAndView mav = handlerMapping.execute(request, response);

        assertThat(handlerMapping.isSupport(request));
        assertThat(mav.getView()).isEqualTo(new RedirectView("/"));
    }
}
