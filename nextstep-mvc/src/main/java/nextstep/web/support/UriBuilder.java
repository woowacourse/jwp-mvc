package nextstep.web.support;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.stream.Collectors;

public class UriBuilder {

    private static final String QUERY_STRING_MARK = "?";
    private static final String KEY_VALUE_SEPARATOR = "=";
    private static final String PARAMETER_SEPARATOR = "&";
    private final String url;
    private final Map<String, String> params = Maps.newHashMap();

    private UriBuilder(final String url) {
        this.url = url;
    }

    public static UriBuilder of(final String url) {
        return new UriBuilder(url);
    }

    public UriBuilder appendParam(String name, String value) {
        params.put(name, value);
        return this;
    }

    public String build() {
        String queryString = params.entrySet().stream()
                .map(entry -> entry.getKey() + KEY_VALUE_SEPARATOR + entry.getValue())
                .collect(Collectors.joining(PARAMETER_SEPARATOR));

        return StringUtils.isEmpty(queryString) ? url : url + QUERY_STRING_MARK + queryString;
    }
}
