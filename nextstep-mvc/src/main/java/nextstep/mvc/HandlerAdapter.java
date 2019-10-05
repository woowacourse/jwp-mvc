package nextstep.mvc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface HandlerAdapter {
    boolean canHandle(Object result);

    void handle(Object result, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
