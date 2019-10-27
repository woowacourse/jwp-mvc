package nextstep.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.exception.ObjectMapperException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JsonUtils {
    public static <T> T toObject(String json, Class<T> clazz) throws ObjectMapperException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setVisibility(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                    .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                    .withGetterVisibility(JsonAutoDetect.Visibility.ANY)
                    .withSetterVisibility(JsonAutoDetect.Visibility.NONE));
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new ObjectMapperException(e);
        }
    }

    public static <T> T requestBodyToObject(HttpServletRequest req, Class<T> clazz) {
        try {
            String requestBody = req.getReader().readLine();
            return toObject(requestBody, clazz);
        } catch (IOException e) {
            throw new ObjectMapperException(e);
        }
    }
}
