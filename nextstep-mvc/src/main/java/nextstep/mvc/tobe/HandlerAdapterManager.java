package nextstep.mvc.tobe;

import nextstep.mvc.Handler;
import nextstep.mvc.HandlerAdapter;
import nextstep.mvc.tobe.exception.InvalidHandlerAdaptException;
import nextstep.mvc.tobe.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class HandlerAdapterManager {

    private final List<HandlerAdapter> handlerAdapters;

    public HandlerAdapterManager(List<HandlerAdapter> handlerAdapters) {
        this.handlerAdapters = handlerAdapters;
    }

    public ModelAndView handle(Handler handler, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return handlerAdapters.stream()
                .filter(handlerAdapter -> handlerAdapter.support(handler))
                .findFirst()
                .orElseThrow(InvalidHandlerAdaptException::new)
                .handle(handler, request, response);
    }
}
