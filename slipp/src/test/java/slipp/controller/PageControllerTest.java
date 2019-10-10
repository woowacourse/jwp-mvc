package slipp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import support.test.NsWebTestClient;

import java.net.URI;
import java.net.URISyntaxException;

class PageControllerTest {
    private MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

    @Test
    void index() throws URISyntaxException {
        NsWebTestClient.of(8080).getRequest(new URI("/"))
                .expectStatus().isOk();
    }
}