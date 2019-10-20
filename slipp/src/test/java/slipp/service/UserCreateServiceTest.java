package slipp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import slipp.dto.UserCreatedDto;

import static org.assertj.core.api.Assertions.assertThat;

class UserCreateServiceTest {
    private static final String DEFAULT_USER_ID = "hyo";
    private static final String DEFAULT_USER_PASSWORD = "123";
    private static final String DEFAULT_USER_NAME = "hyojae";
    private static final String DEFAULT_USER_EMAIL = "hyo@test.com";

    private UserCreateService userCreateService = new UserCreateService();
    private MockHttpServletRequest request;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();

        request.addParameter("userId", DEFAULT_USER_ID);
        request.addParameter("password", DEFAULT_USER_PASSWORD);
        request.addParameter("name", DEFAULT_USER_NAME);
        request.addParameter("email", DEFAULT_USER_EMAIL);
    }

    @Test
    @DisplayName("유저를 정상적으로 생성하고, DB에 저장한다.")
    void addUser() {
        UserCreatedDto userCreatedDto = new UserCreatedDto(DEFAULT_USER_ID,
                                                            DEFAULT_USER_PASSWORD,
                                                            DEFAULT_USER_NAME,
                                                            DEFAULT_USER_EMAIL);

        assertThat(userCreateService.addUser(userCreatedDto)).isEqualTo(DEFAULT_USER_ID);
    }
}