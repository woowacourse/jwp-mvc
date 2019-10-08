package support.test;

import org.apache.logging.log4j.util.Strings;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.Map;

import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

public class WebTestClient {
    private static final String BASE_URL = "http://localhost";

    private String baseUrl = BASE_URL;
    private int port;
    private org.springframework.test.web.reactive.server.WebTestClient.Builder testClientBuilder;

    private WebTestClient(String baseUrl, int port) {
        this.baseUrl = baseUrl;
        this.port = port;
        this.testClientBuilder = org.springframework.test.web.reactive.server.WebTestClient
                .bindToServer()
                .baseUrl(baseUrl + ":" + port);
    }

    public static WebTestClient of(int port) {
        return of(BASE_URL, port);
    }

    private static WebTestClient of(String baseUrl, int port) {
        return new WebTestClient(baseUrl, port);
    }

    public WebTestClient basicAuth(String username, String password) {
        this.testClientBuilder = testClientBuilder.filter(basicAuthentication(username, password));
        return this;
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

    private <T> BodyInserters.FormInserter<String> createFormData(Class<T> classType, Map<String, String> parameters) {
        BodyInserters.FormInserter<String> body = BodyInserters.fromFormData(Strings.EMPTY, Strings.EMPTY);
        for (int i = 0; i < classType.getDeclaredFields().length; i++) {
            body.with(classType.getDeclaredFields()[i].getName(), parameters.get(classType.getDeclaredFields()[i].getName()));
        }
        return body;
    }
}
