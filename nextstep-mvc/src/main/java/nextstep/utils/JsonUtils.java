package nextstep.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.exception.ObjectMapperException;

import java.io.IOException;
import java.io.InputStream;

public class JsonUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setVisibility(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.ANY)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE));
    }

    public static <T> T toObject(String json, Class<T> clazz) throws ObjectMapperException {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new ObjectMapperException(e);
        }
    }

    public static <T> T toObject(InputStream inputStream, Class<T> clazz) throws ObjectMapperException {
        try {
            return objectMapper.readValue(inputStream, clazz);
        } catch (IOException e) {
            throw new ObjectMapperException(e);
        }
    }
}
