package nextstep.web.support;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.stream.Collectors;

public class ResponseLocationBuilder {

    private static final String QUERY_STRING_MARK = "?";
    private static final String KEY_VALUE_SEPARATOR = "=";
    private static final String PARAMETER_SEPARATOR = "&";
    private final String url;
    private final Map<String, String> params = Maps.newHashMap();

    private ResponseLocationBuilder(final String url) {
        this.url = url;
    }

    public static ResponseLocationBuilder of(final String url) {
        return new ResponseLocationBuilder(url);
    }

    public ResponseLocationBuilder appendParam(String name, String value) {
        params.put(name, value);
        return this;
    }

    public String build() {
        String queryString = params.entrySet().stream()
                .map(entry -> entry.getKey() + KEY_VALUE_SEPARATOR + entry.getValue())
                .collect(Collectors.joining(PARAMETER_SEPARATOR));

        return url + QUERY_STRING_MARK + queryString;
    }
}
