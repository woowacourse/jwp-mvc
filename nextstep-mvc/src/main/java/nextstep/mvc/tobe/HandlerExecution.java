package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.function.BiFunction;

@FunctionalInterface
public interface HandlerExecution extends BiFunction<HttpServletRequest, HttpServletResponse, Optional<ModelAndView>> {

    @Override
    default Optional<ModelAndView> apply(HttpServletRequest request, HttpServletResponse httpServletResponse) {
        return Optional.of(handle(request, httpServletResponse));
    }

    ModelAndView handle(HttpServletRequest request, HttpServletResponse response);
}
