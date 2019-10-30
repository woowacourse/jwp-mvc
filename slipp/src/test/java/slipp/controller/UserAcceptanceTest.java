package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import slipp.domain.User;
import slipp.dto.UserCreatedDto;
import slipp.dto.UserUpdatedDto;
import support.test.NsWebTestClient;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class UserAcceptanceTest {
    private NsWebTestClient client;
    private URI userLocation;
    private UserCreatedDto expected;

    @BeforeEach
    void setUp() {
        client = NsWebTestClient.of(8080);

    }

    @Test
    @DisplayName("사용자 회원 가입")
    void createUser() throws URISyntaxException {
        // Given
        UserCreatedDto createdDto = new UserCreatedDto(
                "pobi",
                "password",
                "포비",
                "pobi@nextstep.camp"
        );
        URI expected = new URI("/api/users?userId=pobi");

        // When
        URI location = client.createResource("/api/users", createdDto, UserCreatedDto.class);

        // Then
        assertThat(location).isEqualTo(expected);
    }

    @Test
    @DisplayName("사용자 조회")
    void findUer() {
        // Given
        UserCreatedDto expected = new UserCreatedDto(
                "pobi",
                "password",
                "포비",
                "pobi@nextstep.camp"
        );
        URI location = client.createResource("/api/users", expected, UserCreatedDto.class);

        // When
        User actual = client.getResource(location, User.class);

        // Then
        assertThat(actual.getUserId()).isEqualTo(expected.getUserId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
    }

    @Test
    @DisplayName("사용자 정보 수정")
    void updateUser() {
        // Given
        UserCreatedDto expected = new UserCreatedDto(
                "pobi",
                "password",
                "포비",
                "pobi@nextstep.camp"
        );
        URI location = client.createResource("/api/users", expected, UserCreatedDto.class);
        UserUpdatedDto updatedUser = new UserUpdatedDto("password2", "코난", "conan@nextstep.camp");

        // When
        client.updateResource(location, updatedUser, UserUpdatedDto.class);

        // Then
        User actual = client.getResource(userLocation, User.class);
        assertThat(actual.getPassword()).isEqualTo(updatedUser.getPassword());
        assertThat(actual.getName()).isEqualTo(updatedUser.getName());
        assertThat(actual.getEmail()).isEqualTo(updatedUser.getEmail());
    }
}
