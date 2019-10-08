package nextstep.mvc.tobe.resolver;

import nextstep.mvc.tobe.TestUser;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ArgumentResolverTest {

    @Test
    void resolve() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContentType("application/json");
        request.setContent("{\"userId\":\"pobi\",\"password\":\"p@ssw0rd\",\"age\":20}".getBytes());

        TestUser testUser = ArgumentResolver.resolve(request, TestUser.class);
        assertThat(testUser.getUserId()).isEqualTo("pobi");
        assertThat(testUser.getPassword()).isEqualTo("p@ssw0rd");
        assertThat(testUser.getAge()).isEqualTo(20);
    }
}