package nextstep.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.exception.ObjectMapperException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

public class JsonUtils {
    public static <T> T toObject(String json, Class<T> clazz) throws ObjectMapperException {
        try {
            ObjectMapper objectMapper = makeJsonObjectMapper();
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new ObjectMapperException(e);
        }
    }

    public static <T> T toObject(InputStream inputStream, Class<T> clazz) throws ObjectMapperException {
        try {
            ObjectMapper objectMapper = makeJsonObjectMapper();
            return objectMapper.readValue(inputStream, clazz);
        } catch (IOException e) {
            throw new ObjectMapperException(e);
        }
    }

    private static ObjectMapper makeJsonObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.ANY)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE));
        return objectMapper;
    }
}
