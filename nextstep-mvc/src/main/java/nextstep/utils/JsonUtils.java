package nextstep.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.ObjectMapperException;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class JsonUtils {
    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
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

    public static <T> T toObject(Reader reader, Class<T> clazz) throws ObjectMapperException {
        try {
            return objectMapper.readValue(reader, clazz);
        } catch (IOException e) {
            throw new ObjectMapperException(e);
        }
    }

    public static void toJson(Writer writer, Object object) {
        try {
            objectMapper.writeValue(writer, object);
        } catch (IOException e) {
            throw new ObjectMapperException(e);
        }
    }
}
