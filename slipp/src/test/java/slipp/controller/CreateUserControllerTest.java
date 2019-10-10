package slipp.controller;

import nextstep.mvc.tobe.core.RequestHandlers;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.RedirectView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import slipp.ManualLegacyHandlerMapping;
import slipp.domain.User;
import slipp.support.db.DataBase;

import static org.assertj.core.api.Assertions.assertThat;

class CreateUserControllerTest {
    private RequestHandlers mappings;

    @BeforeEach
    void setUp() {
        mappings = new RequestHandlers(new ManualLegacyHandlerMapping(), "slipp.controller");
        mappings.initialize();
    }

    @Test
    @DisplayName("레거시 컨트롤러를 바꾸었을때 정상 동작")
    void crud() throws Exception {
        // 회원가입
        User expected = new User("sloth", "password", "redman", "marx@communism.rus");
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/users/create");
        request.setParameter("userId", expected.getUserId());
        request.setParameter("password", expected.getPassword());
        request.setParameter("name", expected.getName());
        request.setParameter("email", expected.getEmail());

        MockHttpServletResponse response = new MockHttpServletResponse();

        ModelAndView mv = mappings.handle(request, response);

        assertThat(DataBase.findUserById("sloth")).isEqualTo(expected);
        assertThat(mv.getView() instanceof RedirectView).isTrue();
    }
}