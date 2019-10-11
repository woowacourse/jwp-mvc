package nextstep.mvc.tobe.view;

import nextstep.web.support.MediaType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseEntityTest {

    @Test
    void ok_ResponseEntity_생성() {
        String body = "hello world!";
        ResponseEntity responseEntity = ResponseEntity.ok(body);

        assertThat(responseEntity.getMediaType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(responseEntity.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(body);
        assertThat(responseEntity.getContentLength()).isEqualTo(body.getBytes().length);
    }

    @Test
    void badRequest_ResponseEntity_생성() {
        String body = "error";
        ResponseEntity responseEntity = ResponseEntity.badRequest(body);

        assertThat(responseEntity.getMediaType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(responseEntity.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isEqualTo(body);
        assertThat(responseEntity.getContentLength()).isEqualTo(body.getBytes().length);
    }
}