package nextstep.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.ObjectMapperException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final int SINGLE_DATA_MODEL_SIZE = 1;

    static {
        OBJECT_MAPPER.setVisibility(OBJECT_MAPPER.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.ANY)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE));
    }

    public static <T> T toObject(String json, Class<T> clazz) throws ObjectMapperException {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            throw new ObjectMapperException(e);
        }
    }

    public static <T> T toObject(InputStream inputStream, Class<T> clazz) throws ObjectMapperException{
        try {
            return OBJECT_MAPPER.readValue(inputStream, clazz);
        } catch (IOException e) {
            throw new ObjectMapperException(e);
        }
    }

    public static String parseToJson(Map<String, ?> model) throws JsonProcessingException {
        return model.size() == SINGLE_DATA_MODEL_SIZE ?
                toSingleTypeJson(model ) : toJson(model);
    }

    private static String toSingleTypeJson(Map<String, ?> model) throws JsonProcessingException {
        Object data = model.values()
                .iterator()
                .next();
        return OBJECT_MAPPER.writeValueAsString(data);
    }

    private static String toJson(Map<String, ?> model) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(model);
    }
}
