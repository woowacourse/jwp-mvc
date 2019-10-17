package support.test;

import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

public class NsWebTestClient {
    private static final String BASE_URL = "http://localhost";

    private String baseUrl = BASE_URL;
    private String sessionId = "";
    private int port;
    private WebTestClient.Builder testClientBuilder;

    private NsWebTestClient(String baseUrl, int port) {
        this.baseUrl = baseUrl;
        this.port = port;
        this.testClientBuilder = WebTestClient
                .bindToServer()
                .baseUrl(baseUrl + ":" + port)
                .responseTimeout(Duration.ofMillis(30000));
    }

    public NsWebTestClient basicAuth(String username, String password) {
        this.testClientBuilder = testClientBuilder.filter(basicAuthentication(username, password));
        return this;
    }

    public <T> URI createResource(String url, T body, Class<T> clazz) {
        EntityExchangeResult<byte[]> response = testClientBuilder.build()
                .post()
                .uri(url)
                .cookie("JSESSIONID", sessionId)
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
                .cookie("JSESSIONID", sessionId)
                .body(Mono.just(body), clazz)
                .exchange()
                .expectStatus().isOk();
    }

    public <T> T getResource(URI location, Class<T> clazz) {
        return testClientBuilder.build()
                .get()
                .uri(location.toString())
                .cookie("JSESSIONID", sessionId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(clazz)
                .returnResult()
                .getResponseBody();
    }

    public EntityExchangeResult<byte[]> getResponse(String url) {
        return testClientBuilder.build()
                .get()
                .uri(url)
                .cookie("JSESSIONID", sessionId)
                .exchange()
                .expectBody()
                .returnResult();
    }

    public static NsWebTestClient of(int port) {
        return of(BASE_URL, port);
    }

    public static NsWebTestClient of(String baseUrl, int port) {
        return new NsWebTestClient(baseUrl, port);
    }

    public EntityExchangeResult<byte[]> postForm(String url, Map<String, String> params) {
        BodyInserters.FormInserter<String> body = BodyInserters.fromFormData("", "");
        for (String key : params.keySet()) {
            body.with(key, params.get(key));
        }

        EntityExchangeResult<byte[]> response = testClientBuilder.build()
            .post()
            .uri(url)
            .cookie("JSESSIONID", sessionId)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(body)
            .exchange()
            .expectStatus()
            .is3xxRedirection()
            .expectBody()
            .returnResult();
        return response;
    }

    public void login(String userId, String password) {
        BodyInserters.FormInserter<String> body = BodyInserters.fromFormData("", "");
        body.with("userId", userId);
        body.with("password", password);

        EntityExchangeResult<byte[]> response = testClientBuilder.build()
                .post()
                .uri("/users/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(body)
                .exchange()
                .expectStatus()
                .is3xxRedirection()
                .expectBody()
                .returnResult();
        sessionId = response.getResponseHeaders().getFirst("Set-Cookie").split(";")[0].split("=")[1];

    }
}
