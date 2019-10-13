package nextstep.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class JsonUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T toObject(InputStream in, Class<T> clazz) throws ObjectMapperException {
        try {
            objectMapper.setVisibility(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                    .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                    .withGetterVisibility(JsonAutoDetect.Visibility.ANY)
                    .withSetterVisibility(JsonAutoDetect.Visibility.NONE));
            return objectMapper.readValue(in, clazz);
        } catch (IOException e) {
            throw new ObjectMapperException(e);
        }
    }
}
