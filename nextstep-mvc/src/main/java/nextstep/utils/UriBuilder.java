package nextstep.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class UriBuilder {
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final String PARAM_DELIMITER = "&";
    private static final String QUERYPARAMS_DELIMITER = "?";
    private static final int LAST_PARAM_DELIMITER = 1;

    private String baseUri;
    private Map<String, String> queryParams;

    private UriBuilder(String baseUri) {
        this.baseUri = baseUri;
        queryParams = new LinkedHashMap<>();
    }

    public static UriBuilder builder(String baseUri) {
        return new UriBuilder(baseUri);
    }

    public UriBuilder appendQueryParams(String key, String val) {
        queryParams.put(key, val);
        return this;
    }

    public String build() {
        return queryParams.isEmpty() ? baseUri : attachQueryParams();
    }

    private String attachQueryParams() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> param : queryParams.entrySet()) {
            sb.append(param.getKey()).append(KEY_VALUE_DELIMITER)
                    .append(param.getValue()).append(PARAM_DELIMITER);
        }
        return baseUri + QUERYPARAMS_DELIMITER + trim(sb);
    }

    private String trim(StringBuilder sb) {
        return sb.substring(0, sb.length() - LAST_PARAM_DELIMITER);
    }
}
