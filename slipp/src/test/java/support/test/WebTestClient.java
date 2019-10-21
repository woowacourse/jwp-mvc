package support.test;

import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.StatusAssertions;
import org.springframework.util.LinkedMultiValueMap;
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

    public static WebTestClient of(int port) {
        return of(BASE_URL, port);
    }

    public static WebTestClient of(String baseUrl, int port) {
        return new WebTestClient(baseUrl, port);
    }

    public WebTestClient basicAuth(String username, String password) {
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

    public StatusAssertions getRequest(String uri) {
        return testClientBuilder.build()
                .get()
                .uri(uri)
                .exchange()
                .expectStatus();
    }

    public StatusAssertions getRequestWithCookie(String uri, String cookie) {
        return testClientBuilder.build()
                .get()
                .uri(uri)
                .header("Cookie", cookie)
                .exchange()
                .expectStatus();
    }

    public <T> StatusAssertions postRequestWithBody(String url, T body, Class<T> clazz) {
        return testClientBuilder.build()
                .post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(body), clazz)
                .exchange()
                .expectStatus();
    }

    public <T> StatusAssertions postRequestWithBody(String url, LinkedMultiValueMap map) {
        return testClientBuilder.build()
                .post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromFormData(map))
                .exchange()
                .expectStatus();
    }

}
