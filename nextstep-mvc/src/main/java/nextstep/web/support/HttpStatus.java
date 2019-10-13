package nextstep.web.support;

public enum HttpStatus {
    OK(200, "OK"),
    CREATED(201, "Created"),
    NO_CONTENT(204, "No Content"),
    FOUND(302, "Found"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private int status;
    private String phrase;

    HttpStatus(int status, String phrase) {
        this.status = status;
        this.phrase = phrase;
    }

    public String getPhrase() {
        return phrase;
    }

    public int getStatus() {
        return status;
    }
}
