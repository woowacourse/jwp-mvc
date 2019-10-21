package support.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.reactive.server.StatusAssertions;
import org.springframework.util.LinkedMultiValueMap;
import slipp.dto.UserCreatedDto;

public class TestHelper {
    private static final Logger logger = LoggerFactory.getLogger(TestHelper.class);

    WebTestClient client;

    public TestHelper(WebTestClient client) {
        this.client = client;
    }

    public StatusAssertions createUser(UserCreatedDto userCreatedDto) {
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("userId", userCreatedDto.getUserId());
        map.add("password", userCreatedDto.getPassword());
        map.add("name", userCreatedDto.getName());
        map.add("email", userCreatedDto.getEmail());
        return client.postRequestWithBody("/users/create", map);
    }

    public String getCookie() {
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("userId", "pobi");
        map.add("password", "password");
        String cookie = WebTestClient.of(8080).postRequestWithBody("/users/login", map)
                .isFound()
                .returnResult(String.class)
                .getResponseHeaders()
                .getFirst("Set-Cookie");
        logger.debug("cookie : {}", cookie);
        return cookie;
    }
}
