package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import com.google.common.io.CharStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class RequestBodyParser {
    private static final Logger logger = LoggerFactory.getLogger(RequestBodyParser.class);
    private static final String DELIMITER_OF_REQUEST_BODY = "&";
    private static final String DELIMITER_OF_REQUEST_KEY_AND_VALUE = "=";

    public static Map<String, String> parse(HttpServletRequest request) {
        Map<String, String> body = Maps.newHashMap();

        try (BufferedReader reader = request.getReader()) {
            String requestBody = CharStreams.toString(reader);
            String[] tokens = requestBody.split(DELIMITER_OF_REQUEST_BODY);

            for (String token : tokens) {
                String[] split = token.split(DELIMITER_OF_REQUEST_KEY_AND_VALUE);
                if (split.length == 2) {
                    body.put(split[0], split[1]);
                }
            }
        } catch (IOException e) {
            logger.debug(e.getMessage());
        }
        return Collections.unmodifiableMap(body);
    }
}
