package support.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.net.URISyntaxException;

public class AcceptanceTestTemplate {
    private static final Logger logger = LoggerFactory.getLogger(AcceptanceTestTemplate.class);

    private static MultiValueMap body = new LinkedMultiValueMap<>();

    public static void signUp() {
        body.clear();
        body.add("userId", "pobi123");
        body.add("password", "passworD1!");
        body.add("name", "pobi");
        body.add("email", "pobi123@gmail.com");

        try {
            NsWebTestClient.of(8080).postRequest(new URI("/users/create"), body)
                    .expectStatus().isFound();
        } catch (URISyntaxException e) {
            logger.error("signUp uri error : {}", e);
        }
    }

    public static void login() {
        body.clear();
        body.add("userId", "pobi123");
        body.add("password", "passworD1!");

        try {
            NsWebTestClient.of(8080).postRequest(new URI("/users/login"), body)
                    .expectStatus().isFound();
        } catch (URISyntaxException e) {
            logger.error("login uri error : {}", e);
        }
    }

    public static String getCookie() throws URISyntaxException {
        body.clear();
        body.add("userId", "pobi123");
        body.add("password", "passworD1!");
        String cookie = NsWebTestClient.of(8080).postRequest(new URI("/users/login"), body)
                .expectStatus().isFound()
                .returnResult(String.class)
                .getResponseHeaders()
                .getFirst("Set-Cookie");

        logger.debug("[cookie] : {}", cookie);
        return cookie;
    }
}
