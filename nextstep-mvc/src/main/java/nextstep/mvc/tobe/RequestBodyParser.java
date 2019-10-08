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
    private static final String DELIMITER_OF_REQUEST_BODY = ",";
    private static final String DELIMITER_OF_REQUEST_KEY_AND_VALUE = ":";
    private static final String START_BRACKET = "{";
    private static final String END_BRACKET = "}";
    private static final String DOUBLE_QUOTATION = "\"";
    private static final String EMPTY = "";

    public static Map<String, String> parse(HttpServletRequest request) {
        Map<String, String> body = Maps.newHashMap();

        try (BufferedReader reader = request.getReader()) {
            String requestBody = CharStreams.toString(reader);
            String[] tokens = extractBody(requestBody).split(DELIMITER_OF_REQUEST_BODY);

            for (String token : tokens) {
                String[] split = token.split(DELIMITER_OF_REQUEST_KEY_AND_VALUE);
                body.put(split[0], split[1]);
            }
        } catch (IOException e) {
            logger.debug(e.getMessage());
        }
        return Collections.unmodifiableMap(body);
    }

    private static String extractBody(String body) {
        body = body.replaceAll(DOUBLE_QUOTATION, EMPTY);
        int startIndex = body.indexOf(START_BRACKET);
        int endIndex = body.indexOf(END_BRACKET);

        return body.substring(startIndex + 1, endIndex);
    }
}
