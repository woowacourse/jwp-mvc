package support.test;

import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.StatusAssertions;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

public class NsWebTestClient {
    private static final String BASE_URL = "http://localhost";

    private String baseUrl = BASE_URL;
    private int port;
    private WebTestClient.Builder testClientBuilder;

    private NsWebTestClient(String baseUrl, int port) {
        this.baseUrl = baseUrl;
        this.port = port;
        this.testClientBuilder = WebTestClient
                .bindToServer()
                .baseUrl(baseUrl + ":" + port);
    }

    public static NsWebTestClient of(int port) {
        return of(BASE_URL, port);
    }

    public static NsWebTestClient of(String baseUrl, int port) {
        return new NsWebTestClient(baseUrl, port);
    }

    public NsWebTestClient basicAuth(String username, String password) {
        this.testClientBuilder = testClientBuilder.filter(basicAuthentication(username, password));
        return this;
    }

    public <T> URI createResource(String url, T body, Class<T> clazz) {
        EntityExchangeResult<byte[]> response = testClientBuilder.build()
                .post()
                .uri(url)
                .body(Mono.just(body), clazz)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .returnResult();
        return response.getResponseHeaders().getLocation();
    }

    public <T> void updateResource(URI location, T body, Class<T> clazz) {
        testClientBuilder.build()
                .put()
                .uri(location.toString())
                .body(Mono.just(body), clazz)
                .exchange()
                .expectStatus().isOk();
    }

    public <T> T getResource(URI location, Class<T> clazz) {
        return testClientBuilder.build()
                .get()
                .uri(location.toString())
                .exchange()
                .expectStatus().isOk()
                .expectBody(clazz)
                .returnResult().getResponseBody();
    }

    public StatusAssertions getRequest(URI location) {
        return testClientBuilder.build()
                .get()
                .uri(location.toString())
                .exchange()
                .expectStatus();
    }

    public StatusAssertions getRequest(URI location, String cookie) {
        return testClientBuilder.build()
                .get()
                .uri(location.toString())
                .header("Cookie", cookie)
                .exchange()
                .expectStatus();
    }

    public StatusAssertions postRequest(URI location, MultiValueMap<String, String> body) {
        return testClientBuilder.build()
                .post()
                .uri(location.toString())
                .body(BodyInserters.fromFormData(body))
                .exchange()
                .expectStatus();
    }
}
