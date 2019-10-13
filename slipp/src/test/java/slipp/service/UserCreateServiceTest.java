package slipp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import slipp.dto.UserCreatedDto;

import static org.assertj.core.api.Assertions.assertThat;

class UserCreateServiceTest {
    private UserCreateService userCreateService = new UserCreateService();

    private MockHttpServletRequest request;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
    }

    @Test
    @DisplayName("유저를 정상적으로 생성하고, DB에 저장한다.")
    void addUser() {
        request.addParameter("userId", "hyo");
        request.addParameter("password", "123");
        request.addParameter("name", "hyojae");
        request.addParameter("email", "hyo@test.com");

        UserCreatedDto userCreatedDto = userCreateService.addUser(request);

        assertThat(userCreatedDto.getUserId()).isEqualTo("hyo");
        assertThat(userCreatedDto.getPassword()).isEqualTo("123");
        assertThat(userCreatedDto.getName()).isEqualTo("hyojae");
        assertThat(userCreatedDto.getEmail()).isEqualTo("hyo@test.com");
    }
}