package nextstep.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public class JsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    public static <T> T toObject(HttpServletRequest req, Class<T> clazz) throws ObjectMapperException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setVisibility(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                    .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                    .withGetterVisibility(JsonAutoDetect.Visibility.ANY)
                    .withSetterVisibility(JsonAutoDetect.Visibility.NONE));
            return objectMapper.readValue(toJson(req), clazz);
        } catch (IOException e) {
            throw new ObjectMapperException(e);
        }
    }

    private static String toJson(HttpServletRequest req) {
        StringBuilder json = new StringBuilder();
        try {
            String line;
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
        } catch (IOException e) {
            logger.error("Request to Json error : {}", e);
        }

        return json.toString();
    }
}
