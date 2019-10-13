package nextstep.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jknack.handlebars.internal.lang3.StringUtils;
import nextstep.mvc.tobe.view.JsonWritingFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Map;

public class JsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

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

    public static <T> T toObject(InputStream inputStream, Class<T> clazz) throws ObjectMapperException {
        try {
            return OBJECT_MAPPER.readValue(inputStream, clazz);
        } catch (IOException e) {
            throw new ObjectMapperException(e);
        }
    }

    public static String toJson(Object object) throws IOException {
        return OBJECT_MAPPER.writeValueAsString(object);
    }

    public static String toJson(Map<String, ?> model) throws IOException {
        if (model.isEmpty()) {
            return StringUtils.EMPTY;
        }

        if (model.size() == 1) {
            Object value = model.values()
                    .iterator()
                    .next();

            return OBJECT_MAPPER.writeValueAsString(value);
        }

        return OBJECT_MAPPER.writeValueAsString(model);
    }

    public static void writeValue(HttpServletResponse response, Object value) {
        try {
            PrintWriter printWriter = response.getWriter();
            printWriter.write(parseType(value));
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            logger.error("Http Request Exception : ", e);
            throw new JsonWritingFailedException();
        }
    }

    private static String parseType(Object value) throws IOException {
        if (value instanceof Map) {
            return toJson((Map<String, ?>) value);
        }

        return toJson(value);
    }
}
