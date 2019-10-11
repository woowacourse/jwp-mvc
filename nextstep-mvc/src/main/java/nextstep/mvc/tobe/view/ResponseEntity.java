package nextstep.mvc.tobe.view;

import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletResponse;

public class ResponseEntity {
    private String mediaType = MediaType.APPLICATION_JSON_UTF8_VALUE;
    private HttpStatus httpStatus;
    private String body;

    private ResponseEntity(HttpStatus httpStatus, String body) {
        this.httpStatus = httpStatus;
        this.body = body;
    }

    public static ResponseEntity of(HttpStatus httpStatus, String body) {
        return new ResponseEntity(httpStatus, body);
    }

    public static ResponseEntity ok(String body) {
        return new ResponseEntity(HttpStatus.OK, body);
    }

    public static ResponseEntity badRequest(String body) {
        return new ResponseEntity(HttpStatus.BAD_REQUEST, body);
    }

    public ResponseEntity mediaType(String mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public ResponseEntity body(String body) {
        this.body = body;
        return this;
    }

    public ResponseEntity httpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getBody() {
        return body;
    }

    void setResponseHeader(HttpServletResponse response) {
        response.setContentType(mediaType);
        response.setStatus(httpStatus.getStatusCode());
        ;
        response.setContentLength(getContentLength());
    }

    private int getContentLength() {
        return body.getBytes().length;
    }
}
