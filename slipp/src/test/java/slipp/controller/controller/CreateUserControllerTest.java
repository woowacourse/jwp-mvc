package slipp.controller.controller;

import nextstep.mvc.tobe.handler.HandlerExecution;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import slipp.domain.User;
import slipp.support.db.DataBase;

class CreateUserControllerTest extends BaseControllerTest {
    private static final Logger log = LoggerFactory.getLogger(CreateUserControllerTest.class);

    @BeforeEach
    void setUp() {
        init();
    }

    @Test
    void requestMappingCreateUser() throws Exception {
        // 회원가입
        User expected = new User("sloth", "password", "슬로스", "natae@jiok.ha");
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/users/create");
        request.setParameter("userId", expected.getUserId());
        request.setParameter("password", expected.getPassword());
        request.setParameter("name", expected.getName());
        request.setParameter("email", expected.getEmail());

        MockHttpServletResponse response = new MockHttpServletResponse();

        HandlerExecution handler = mappingHandler(request, response);
        handler.handle(request, response);

        Assertions.assertThat(DataBase.findUserById("sloth")).isEqualTo(expected);
    }

}