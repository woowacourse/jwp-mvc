package nextstep.mvc;

import nextstep.mvc.tobe.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.function.BiFunction;

public interface HandlerMapping {
    void initialize();

    BiFunction<HttpServletRequest, HttpServletResponse, Optional<ModelAndView>> getHandler(HttpServletRequest request);
}
