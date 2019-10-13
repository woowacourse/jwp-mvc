package slipp.support.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static nextstep.utils.JsonUtils.toObject;

public class BodyParser {
    public static <T> T parseBody(HttpServletRequest req, Class<T> clazz) throws IOException {
        return toObject(req.getReader().readLine(), clazz);
    }
}
