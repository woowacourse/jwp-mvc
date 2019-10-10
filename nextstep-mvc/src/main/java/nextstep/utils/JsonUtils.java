package nextstep.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.exception.ObjectMapperException;

import javax.servlet.ServletInputStream;
import java.io.IOException;

public class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> T toObject(String json, Class<T> clazz) throws ObjectMapperException {
        try {
            OBJECT_MAPPER.setVisibility(OBJECT_MAPPER.getSerializationConfig().getDefaultVisibilityChecker()
                    .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                    .withGetterVisibility(JsonAutoDetect.Visibility.ANY)
                    .withSetterVisibility(JsonAutoDetect.Visibility.NONE));
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            throw new ObjectMapperException(e);
        }
    }

    public static <T> T toObject(final ServletInputStream servletInputStream, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(servletInputStream, clazz);
        } catch (IOException e) {
            throw new ObjectMapperException(e);
        }
    }
}
