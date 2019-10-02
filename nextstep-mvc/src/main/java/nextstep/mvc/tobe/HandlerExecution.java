package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.function.BiFunction;

@FunctionalInterface
public interface HandlerExecution {
    ModelAndView handle(HttpServletRequest request, HttpServletResponse response);
}
