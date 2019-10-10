package slipp.support.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BodyParser {
    public static String getBody(HttpServletRequest request) throws IOException {
        InputStream inputStream = request.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        int contentLength = request.getContentLength();
        char[] body = new char[contentLength];

        bufferedReader.read(body, 0, contentLength);

        return String.copyValueOf(body);
    }
}
