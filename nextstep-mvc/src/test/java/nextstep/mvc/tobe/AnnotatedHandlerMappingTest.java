package nextstep.mvc.tobe;

import nextstep.db.DataBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

public class AnnotatedHandlerMappingTest {
    private AnnotatedHandlerMapping handlerMapping;

    @BeforeEach
    public void setup() {
        this.handlerMapping = new AnnotatedHandlerMapping("slipp");
    }

    @Test
    public void create_find() throws Exception {
        final User user = new User("pobi", "password", "포비", "pobi@nextstep.camp");
        final ModelAndView mv = createUser(user);
        assertThat(DataBase.findUserById(user.getUserId())).isEqualTo(user);

        final MockHttpServletRequest req = new MockHttpServletRequest("GET", "/users");
        req.setParameter("userId", user.getUserId());
        this.handlerMapping.getHandler(req).run(req, new MockHttpServletResponse());

        assertThat(req.getAttribute("user")).isEqualTo(user);
    }

    private ModelAndView createUser(User user) throws Exception {
        final MockHttpServletRequest req = new MockHttpServletRequest("POST", "/users/create");
        req.setParameter("userId", user.getUserId());
        req.setParameter("password", user.getPassword());
        req.setParameter("name", user.getName());
        req.setParameter("email", user.getEmail());
        return this.handlerMapping.getHandler(req).run(req, new MockHttpServletResponse());
    }
}
