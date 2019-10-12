package slipp.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {
    private User defaultUser;

    @BeforeEach
    void setUp() {
        defaultUser = new User("done", "1234", "done", "done@gmail.com");
    }

    @Test
    @DisplayName("회원정보 수정")
    public void updateUserInfo() {
        defaultUser.update(new User("done", "12", "done", "done@slipp.net"));
        assertThat(defaultUser).isEqualTo(new User("done", "12", "done", "done@slipp.net"));
    }

    @Test
    @DisplayName("비밀번호 일치")
    void matchPassword() {
        assertThat(defaultUser.matchPassword("1234")).isTrue();
    }

    @Test
    @DisplayName("비밀번호 불일치")
    void notMatchPassword() {
        assertThat(defaultUser.matchPassword("123")).isFalse();
    }

    @Test
    @DisplayName("비밀번호를 입력하지 않음")
    void nullPassword() {
        assertThat(defaultUser.matchPassword(null)).isFalse();
    }

    @Test
    @DisplayName("동일한 회원인지 확인")
    void sameUser() {
        assertThat(defaultUser.isSameUser(new User("done", "1234", "done", "done@gmail.com")))
                .isTrue();
    }

    @Test
    @DisplayName("동일하지 않은 회원인지 확인")
    void notSameUser() {
        assertThat(defaultUser.isSameUser(new User("pobi", "1234", "done", "done@gmail.com")))
                .isFalse();
    }
}
