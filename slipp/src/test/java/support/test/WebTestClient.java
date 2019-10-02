package support.test;

import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.HeaderAssertions;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.net.URI;

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

    public WebTestClient basicAuth(String username, String password) {
        this.testClientBuilder = testClientBuilder.filter(basicAuthentication(username, password));
        return this;
    }

    public <T> org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec createResource(String url, T body, Class<T> clazz) {
        return testClientBuilder.build()
                .post()
                .uri(url)
                .body(Mono.just(body), clazz)
                .exchange()
                .expectStatus().isFound();
    }

    public <T> void updateResource(String location, T body, Class<T> clazz) {
        testClientBuilder.build()
                .put()
                .uri(location)
                .body(Mono.just(body), clazz)
                .exchange()
                .expectStatus().isOk();
    }

    public <T> T getResource(String location, Class<T> clazz) {
        return testClientBuilder.build()
                .get()
                .uri(location)
                .exchange()
                .expectStatus().isOk()
                .expectBody(clazz)
                .returnResult().getResponseBody();
    }

    public static WebTestClient of(int port) {
        return of(BASE_URL, port);
    }

    public static WebTestClient of(String baseUrl, int port) {
        return new WebTestClient(baseUrl, port);
    }
}
