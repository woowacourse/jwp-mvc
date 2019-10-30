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

public class UserAcceptanceTest2 {
    private NsWebTestClient client;
    private URI testUserLocation;
    private UserCreatedDto testUserDto;

    @BeforeEach
    void setUp() {
        client = NsWebTestClient.of(8080);

        testUserDto = new UserCreatedDto(
                "pobi",
                "password",
                "포비",
                "pobi@nextstep.camp"
        );
        testUserLocation = client.createResource("/api/users", testUserDto, UserCreatedDto.class);
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
        User actual = client.getResource(testUserLocation, User.class);

        assertThat(actual.getUserId()).isEqualTo(testUserDto.getUserId());
        assertThat(actual.getName()).isEqualTo(testUserDto.getName());
        assertThat(actual.getEmail()).isEqualTo(testUserDto.getEmail());
    }

    @Test
    @DisplayName("사용자 정보 수정")
    void updateUser() {
        UserUpdatedDto updatedUser = new UserUpdatedDto("password2", "코난", "conan@nextstep.camp");

        client.updateResource(testUserLocation, updatedUser, UserUpdatedDto.class);

        User actual = client.getResource(testUserLocation, User.class);
        assertThat(actual.getPassword()).isEqualTo(updatedUser.getPassword());
        assertThat(actual.getName()).isEqualTo(updatedUser.getName());
        assertThat(actual.getEmail()).isEqualTo(updatedUser.getEmail());
    }
}
