package nextstep.mvc.tobe.view;

public enum HttpStatus {
    OK(200),
    CREATE(201),
    FOUND(302),
    BAD_REQUEST(400),
    NOT_FOUND(404);

    private final int statusCode;

    HttpStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
