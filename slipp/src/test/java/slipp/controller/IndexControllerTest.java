package slipp.controller;

import org.junit.jupiter.api.Test;
import support.test.NsWebTestClient;

import java.net.URI;
import java.net.URISyntaxException;

class IndexControllerTest {
    @Test
    void index() throws URISyntaxException {
        NsWebTestClient.of(8080).getRequest(new URI("/"))
                .expectStatus().isOk();
    }
}