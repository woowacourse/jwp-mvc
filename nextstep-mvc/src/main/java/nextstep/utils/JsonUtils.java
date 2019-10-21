package nextstep.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.ObjectMapperException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class JsonUtils {
    public static <T> T toObject(String json, Class<T> clazz) throws ObjectMapperException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setVisibility(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                    // [TODO] Visibility 가 무슨 역할을 할까요?
                    // 모두를 NONE 으로 하면 JsonUtilsTest 가 터짐
                    // FieldVisibility 나 GetterVisibility 를 ANY 로 하면 성공
                    // -> 왜 Setter 는 안되는데 나머지 두가지는 영향을 미칠까?
                    //    값을 넣어주는 건 별개이고.. 필드나 Getter 를 통해서 존재여부를 확인하는 건가??
                    .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                    .withGetterVisibility(JsonAutoDetect.Visibility.ANY)
                    .withSetterVisibility(JsonAutoDetect.Visibility.NONE));
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new ObjectMapperException(e);
        }
    }

    // toJson
    public static String toJson(Object instance) {
        ObjectMapper objectMapper = new ObjectMapper();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            return objectMapper.writeValueAsString(instance);
//            objectMapper.writeValue(outputStream, instance);
//
//            return outputStream.toString();
        } catch (IOException e) {
            throw new ObjectMapperException(e);
        }
    }
}
