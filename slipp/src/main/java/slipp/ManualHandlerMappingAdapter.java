package slipp;

import nextstep.mvc.HandlerMapping;
import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.JspView;
import nextstep.mvc.tobe.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

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
    public HandlerExecution getHandler(HttpServletRequest request) {
        Controller controller = handlerMapping.getHandler(request);
        if (controller == null) {
            return null;
        }

        return (req, resp) -> {
            try {
                return new ModelAndView(new JspView(controller.execute(req, resp)));
            } catch (Exception e) {
                logger.error("Failed to getting handler", e);
                throw new RuntimeException(e);
            }
        };
    }
}
