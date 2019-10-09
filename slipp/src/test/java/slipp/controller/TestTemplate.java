package slipp.controller;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.Objects;

public class TestTemplate {
    private static final String BASE = "http://localhost:8080";
    protected static final String USER_ID = "admin";
    protected static final String USER_PASSWORD = "password";

    private WebTestClient.Builder testClientBuilder = WebTestClient
            .bindToServer()
            .baseUrl(BASE);

    protected WebTestClient.ResponseSpec request(HttpMethod method, String uri, HttpStatus httpStatus) {
        return testClientBuilder.build()
                .method(method)
                .uri(uri)
                .exchange()
                .expectStatus()
                .isEqualTo(httpStatus)
                ;
    }

    protected WebTestClient.ResponseSpec requestWithFormData(HttpMethod method,
                                                             String uri,
                                                             BodyInserters.FormInserter<String> form,
                                                             HttpStatus httpStatus) {
        return testClientBuilder.build()
                .method(method)
                .uri(uri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(form)
                .exchange()
                .expectStatus()
                .isEqualTo(httpStatus)
                ;
    }

    protected WebTestClient.ResponseSpec loginAndRequest(HttpMethod method, String uri, HttpStatus httpStatus) {
        return testClientBuilder.build()
                .method(method)
                .uri(uri)
                .cookie("JSESSIONID", getSessionId(USER_ID, USER_PASSWORD))
                .exchange()
                .expectStatus()
                .isEqualTo(httpStatus)
                ;
    }

    protected WebTestClient.ResponseSpec loginAndRequestWithFormData(HttpMethod method,
                                                                     String uri,
                                                                     BodyInserters.FormInserter<String> form,
                                                                     HttpStatus httpStatus) {
        return testClientBuilder.build()
                .method(method)
                .uri(uri)
                .cookie("JSESSIONID", getSessionId(USER_ID, USER_PASSWORD))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(form)
                .exchange()
                .expectStatus()
                .isEqualTo(httpStatus)
                ;
    }

    private String getSessionId(String userId, String password) {
        return Objects.requireNonNull(testClientBuilder.build()
                .post().uri("/users/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("userId", userId)
                        .with("password", password))
                .exchange()
                .returnResult(String.class)
                .getResponseCookies()
                .getFirst("JSESSIONID"))
                .getValue();
    }
}
