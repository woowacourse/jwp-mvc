package nextstep.mvc.tobe.resolver;

import nextstep.mvc.tobe.exception.RequestBodyReadFailedException;
import nextstep.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

public class HttpRequestBodyResolver {

    private HttpRequestBodyResolver() {
    }

    public static <T> T resolve(HttpServletRequest request, Class<T> valueType) {
        try (InputStream inputStream = request.getInputStream();) {
            return JsonUtils.toObject(inputStream, valueType);
        } catch (IOException e) {
            throw new RequestBodyReadFailedException(e);
        }
    }
}
