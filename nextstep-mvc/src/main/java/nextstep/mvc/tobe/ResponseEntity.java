package nextstep.mvc.tobe;

import nextstep.web.support.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class ResponseEntity<T> {
    private HttpStatus httpStatus;
    private Map<String, String> httpHeaders;
    private T body;

    private ResponseEntity(HttpStatus httpStatus, Map<String, String> httpHeaders, T body) {
        this.httpStatus = httpStatus;
        this.httpHeaders = httpHeaders;
        this.body = body;
    }

    private ResponseEntity(HttpStatus httpStatus, T body) {
        this.httpStatus = httpStatus;
        this.body = body;
    }

    public static BodyBuilder of(HttpStatus httpStatus) {
        return new BodyBuilder(httpStatus);
    }

    public static BodyBuilder ok() {
        return new BodyBuilder(HttpStatus.OK);
    }

    public static BodyBuilder created() {
        return new BodyBuilder(HttpStatus.CREATED);
    }

    public static BodyBuilder noContent() {
        return new BodyBuilder(HttpStatus.NO_CONTENT);
    }

    public static BodyBuilder found() {
        return new BodyBuilder(HttpStatus.FOUND);
    }

    public static BodyBuilder notFound() {
        return new BodyBuilder(HttpStatus.NOT_FOUND);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Map<String, String> getHttpHeaders() {
        return httpHeaders;
    }

    public T getBody() {
        return body;
    }

    public static class BodyBuilder {
        private HttpStatus httpStatus;
        private Map<String, String> httpHeaders = new HashMap<>();

        private BodyBuilder() {
        }

        private BodyBuilder(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
        }

        public BodyBuilder contentType(String contentType) {
            httpHeaders.put("Content-Type", contentType);
            return this;
        }

        public BodyBuilder location(String location) {
            httpHeaders.put("Location", location);
            return this;
        }

        public <T> ResponseEntity<T> body(T body) {
            return new ResponseEntity<>(httpStatus, httpHeaders, body);
        }

        public <T> ResponseEntity<T> build() {
            return new ResponseEntity<>(httpStatus, httpHeaders, null);
        }
    }
}
