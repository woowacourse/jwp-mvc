package slipp;

import nextstep.mvc.HandlerMapping;
import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.JspView;
import nextstep.mvc.tobe.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.function.BiFunction;

public class ManualHandlerMappingAdapter implements HandlerMapping {

    private static final Logger logger = LoggerFactory.getLogger(ManualHandlerMappingAdapter.class);
    private final ManualHandlerMapping handlerMapping;

    public ManualHandlerMappingAdapter(ManualHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @Override
    public void initialize() {
        handlerMapping.initialize();
    }

    @Override
    public BiFunction<HttpServletRequest, HttpServletResponse, Optional<ModelAndView>> getHandler(HttpServletRequest request) {
        return (req, resp) -> {
            Controller controller = handlerMapping.getHandler(req);
            if (controller == null) {
                return Optional.empty();
            }
            try {
                return Optional.of(new ModelAndView(new JspView(controller.execute(req, resp))));
            } catch (Exception e) {
                logger.error("Failed to getting handler", e);
            }
            return Optional.empty();
        };
    }
}
