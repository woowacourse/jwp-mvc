package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;

public interface Handler {
    ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException;
}
