package support.test;

import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;
import java.util.Map;

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
            .responseTimeout(Duration.ofMinutes(5))
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
                .returnResult()
                .getResponseBody();
    }

    public EntityExchangeResult<byte[]> getResponse(String url) {
        return testClientBuilder.build()
                .get()
                .uri(url)
                .exchange()
                .expectBody()
                .returnResult();
    }

    public void responseOk(String url) {
        testClientBuilder.build()
            .get()
            .uri(url)
            .exchange()
            .expectStatus()
            .isOk();
    }

    public void postResponse(String url, Map<String, String> params) {
        BodyInserters.FormInserter<String> body = BodyInserters.fromFormData("", "");
        for (String key : params.keySet()) {
            body.with(key, params.get(key));
        }

        testClientBuilder.build()
            .post()
            .uri(url)
            .body(body)
            .exchange()
            .expectStatus()
            .isOk();
    }

    public URI postForm(String url, Map<String, String> params) {
        BodyInserters.FormInserter<String> body = BodyInserters.fromFormData("", "");
        for (String key : params.keySet()) {
            body.with(key, params.get(key));
        }

        EntityExchangeResult<byte[]> response = testClientBuilder.build()
            .post()
            .uri(url)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(body)
            .exchange()
            .expectStatus()
            .is3xxRedirection()
            .expectBody()
            .returnResult();
        return response.getResponseHeaders().getLocation();
    }

    public WebTestClient.ResponseSpec updateForm(String url, Map<String, String> params, String cookie) {
        BodyInserters.FormInserter<String> body = BodyInserters.fromFormData("", "");

        for (String key : params.keySet()) {
            body.with(key, params.get(key));
        }
        return testClientBuilder.build()
            .post()
            .uri(url)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .cookie("JSESSIONID",cookie)
            .body(body)
            .exchange()
            .expectStatus()
            .isOk();
    }

    public WebTestClient.ResponseSpec getCookieResponse(String url, String cookie) {
        return testClientBuilder.build()
            .get()
            .uri(url)
            .cookie("JSESSIONID", cookie)
            .exchange();
    }

    public String getLoginCookie(String url, Map<String, String> params) {
        BodyInserters.FormInserter<String> body = BodyInserters.fromFormData("", "");
        for (String key : params.keySet()) {
            body.with(key, params.get(key));
        }

        return testClientBuilder.build()
            .post()
            .uri(url)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(body)
            .exchange()
            .expectStatus()
            .is3xxRedirection()
            .expectBody()
            .returnResult()
            .getResponseCookies()
            .getFirst("JSESSIONID")
            .getValue();
    }
}
