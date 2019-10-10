package support.test;

import org.springframework.test.web.reactive.server.WebTestClient;

public class CustomWebTestClient {

    private static final String BASE_URL = "http://localhost";

    private String baseUrl = BASE_URL;
    private int port;
    private WebTestClient.Builder testClientBuilder;

    private CustomWebTestClient(String baseUrl, int port) {
        this.baseUrl = baseUrl;
        this.port = port;
        this.testClientBuilder = WebTestClient
                .bindToServer()
                .baseUrl(baseUrl + ":" + port);
    }

    public WebTestClient build() {
        return this.testClientBuilder.build();
    }

    public static CustomWebTestClient of(int port) {
        return of(BASE_URL, port);
    }

    public static CustomWebTestClient of(String baseUrl, int port) {
        return new CustomWebTestClient(baseUrl, port);
    }
}
