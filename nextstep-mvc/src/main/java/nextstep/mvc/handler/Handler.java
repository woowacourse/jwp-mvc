package nextstep.mvc.handler;


import nextstep.mvc.tobe.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Handler {
    Logger log = LoggerFactory.getLogger(Handler.class);

    ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception;

    default void render(HttpServletRequest request, HttpServletResponse response) {
        try {
            ModelAndView modelAndView = this.handle(request, response);
            modelAndView.getView().render(modelAndView.getModel(), request, response);
        } catch (Exception e) {
            log.error("Exception : {}", e);
            throw new IllegalStateException(e);
        }
    }
}
