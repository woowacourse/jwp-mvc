package nextstep.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.exception.ObjectMapperException;

import java.io.IOException;
import java.io.InputStream;

public class JsonUtils {

    private JsonUtils() {
    }

    public static <T> T toObject(String json, Class<T> valueType) {
        try {
            ObjectMapper objectMapper = createObjectMapperWithSettingVisibility();
            return objectMapper.readValue(json, valueType);
        } catch (IOException e) {
            throw new ObjectMapperException(e);
        }
    }

    public static <T> T toObject(InputStream src, Class<T> valueType) {
        try {
            ObjectMapper objectMapper = createObjectMapperWithSettingVisibility();
            return objectMapper.readValue(src, valueType);
        } catch (IOException e) {
            throw new ObjectMapperException(e);
        }
    }

    private static ObjectMapper createObjectMapperWithSettingVisibility() {
        ObjectMapper objectMapper = new ObjectMapper();
        setVisibility(objectMapper);
        return objectMapper;
    }

    private static void setVisibility(final ObjectMapper objectMapper) {
        objectMapper.setVisibility(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.ANY)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE));
    }

    public static String toJson(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new ObjectMapperException(e);
        }
    }
}
