package support.test;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.BodyInserters;
import slipp.dto.UserLoginDto;

import java.util.HashMap;
import java.util.Map;

public class WebTestClient {
    private static final Logger log = LoggerFactory.getLogger(WebTestClient.class);
    private static final String BASE_URL = "http://localhost";
    private static final String  JSESSTIONID = "JSESSIONID";

    private String baseUrl = BASE_URL;
    private int port;
    private org.springframework.test.web.reactive.server.WebTestClient.Builder testClientBuilder;
    private String sessionId;


    private WebTestClient(String baseUrl, int port) {
        this.baseUrl = baseUrl;
        this.port = port;
        this.testClientBuilder = org.springframework.test.web.reactive.server.WebTestClient
                .bindToServer()
                .baseUrl(baseUrl + ":" + port);
    }

    public <T> org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec loginPostResource(String url, Map<String, String> body, Class<T> clazz) {
        return testClientBuilder.build()
                .post()
                .uri(url)
                .cookie(JSESSTIONID, sessionId)
                .body(createFormData(clazz, body))
                .exchange();
    }

    public <T> org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec loginGetResource(String url) {
        return testClientBuilder.build()
                .get()
                .uri(url)
                .cookie(JSESSTIONID, sessionId)
                .exchange();
    }


    public void login() {
        Map<String, String> expected = new HashMap<>();
        expected.put("userId", "admin");
        expected.put("password", "password");
        testClientBuilder.build()
                .post()
                .uri("users/login")
                .body(createFormData(UserLoginDto.class, expected))
                .exchange()
                .expectBody()
                .consumeWith(result -> {
                    sessionId = result.getResponseCookies().get(JSESSTIONID).toString().split(";")[0].split("=")[1];
                    log.info("JSESSIONID = {}", sessionId);
                });
    }

    public <T> org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec postResource(String url, Map<String, String> body, Class<T> clazz) {
        return testClientBuilder.build()
                .post()
                .uri(url)
                .body(createFormData(clazz, body))
                .exchange();
    }

    public org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec getResource(String url) {
        return testClientBuilder.build()
                .get()
                .uri(url)
                .exchange();
    }

    public static WebTestClient of(int port) {
        return of(BASE_URL, port);
    }

    private static WebTestClient of(String baseUrl, int port) {
        return new WebTestClient(baseUrl, port);
    }

    private <T> BodyInserters.FormInserter<String> createFormData(Class<T> classType, Map<String, String> parameters) {
        BodyInserters.FormInserter<String> body = BodyInserters.fromFormData(Strings.EMPTY, Strings.EMPTY);
        for (int i = 0; i < classType.getDeclaredFields().length; i++) {
            body.with(classType.getDeclaredFields()[i].getName(), parameters.get(classType.getDeclaredFields()[i].getName()));
        }
        return body;
    }
}
