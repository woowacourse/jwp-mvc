package nextstep.mvc.tobe.resolver;

import nextstep.mvc.tobe.exception.HttpServletRequestGetBodyException;
import nextstep.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ArgumentResolver {

    private ArgumentResolver() {
    }

    public static <T> T resolve(HttpServletRequest request, Class<T> clazz) {
        String body = getBody(request);
        return JsonUtils.toObject(body, clazz);
    }

    private static String getBody(HttpServletRequest request) {
        try (InputStream inputStream = request.getInputStream();
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder stringBuilder = new StringBuilder();

            char[] charBuffer = new char[128];
            int bytesRead;
            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                stringBuilder.append(charBuffer, 0, bytesRead);
            }

            return stringBuilder.toString();
        } catch (IOException e) {
            throw new HttpServletRequestGetBodyException(e);
        }
    }
}
