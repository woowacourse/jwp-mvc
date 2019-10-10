package nextstep.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import nextstep.mvc.tobe.ObjectMapperException;

import java.io.IOException;
import java.util.Map;

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

    public static String toJsonString(Map<String, ?> model) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            if (model.size() == 1) {
                String key = model.keySet()
                        .iterator().next();
                return objectMapper.writeValueAsString(model.get(key));
            } else if (model.size() > 1) {
                return objectMapper.writeValueAsString(model);
            }
            return "";
        } catch (JsonProcessingException e) {
            throw new ObjectMapperException(e);
        }
    }
}
