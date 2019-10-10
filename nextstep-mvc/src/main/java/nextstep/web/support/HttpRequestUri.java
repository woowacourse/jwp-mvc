package nextstep.web.support;

public class HttpRequestUri implements RequestUri {
    private static final String DELIMITER_OF_QUERY_STRING = "?";
    private final String uri;

    public HttpRequestUri(final String uri) {
        this.uri = uri;
    }

    @Override
    public String extract() {
        if (uri.contains(DELIMITER_OF_QUERY_STRING)) {
            int index = uri.indexOf(DELIMITER_OF_QUERY_STRING);
            return uri.substring(0, index);
        }
        return uri;
    }
}
