package nextstep.utils;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class HttpUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    public static String generateRequestBody(HttpServletRequest request) {
        try {
            return IOUtils.toString(request.getReader());
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
