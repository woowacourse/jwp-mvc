package nextstep.web.support;

public enum MediaType {
    APPLICATION_JSON_UTF8_VALUE("application/json;charset=UTF-8"),
    PLAIN_TEXT("text/plain");

    private String type;

    MediaType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
