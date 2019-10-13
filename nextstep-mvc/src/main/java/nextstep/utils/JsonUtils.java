package nextstep.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.ObjectMapperException;

import java.io.IOException;
import java.util.Map;

public class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String BLANK = "";

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

    public static String toJsonString(Map<String, ?> model) {
        int modelSize = model.size();
        try {
            if (modelSize == 1) {
                return OBJECT_MAPPER.writeValueAsString(
                        model.values().stream().findFirst().get());
            }else if(modelSize > 1)
            return OBJECT_MAPPER.writeValueAsString(model);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return BLANK;
    }
}

