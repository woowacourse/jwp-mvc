package nextstep.mvc.handleradapter;

import nextstep.mvc.tobe.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdapter {
    Logger log = LoggerFactory.getLogger(HandlerAdapter.class);

    boolean supports(Object handler);

    ModelAndView handle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception;

    default void render(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            ModelAndView modelAndView = this.handle(request, response, handler);
            modelAndView.getView().render(modelAndView.getModel(), request, response);
        } catch (Exception e) {
            log.error("Exception : {}", e);
            throw new IllegalStateException(e);
        }
    }
}
