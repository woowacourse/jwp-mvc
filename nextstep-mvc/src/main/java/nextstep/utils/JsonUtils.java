package nextstep.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.ObjectMapperException;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Map;

public class JsonUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T toObject(String json, Class<T> clazz) throws ObjectMapperException {
        try {
            objectMapper = new ObjectMapper();
            objectMapper.setVisibility(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                    .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                    .withGetterVisibility(JsonAutoDetect.Visibility.ANY)
                    .withSetterVisibility(JsonAutoDetect.Visibility.NONE));
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new ObjectMapperException(e);
        }
    }


    public static String toJson(Map<String, ?> model) {
        try {
            if (model.size() == 0) {
                return StringUtils.EMPTY;
            }
            if (model.size() == 1) {
                Object attribute = model.values()
                        .iterator()
                        .next();
                return objectMapper.writeValueAsString(attribute);
            }
            return objectMapper.writeValueAsString(model);
        } catch (IOException e) {
            throw new ObjectMapperException(e);
        }
    }
}
