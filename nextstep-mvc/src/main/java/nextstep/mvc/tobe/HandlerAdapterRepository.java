package nextstep.mvc.tobe;

import nextstep.mvc.exception.AdapterNotFoundException;
import nextstep.mvc.tobe.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class HandlerAdapterRepository {
    private final List<HandlerAdapter> handlerAdapters;

    public HandlerAdapterRepository(List<HandlerAdapter> handlerAdapters) {
        this.handlerAdapters = handlerAdapters;
    }

    public ModelAndView adapt(Object handler, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {

        HandlerAdapter adapter = handlerAdapters.stream()
                .filter(handlerAdapter -> handlerAdapter.supports(handler))
                .findAny().orElseThrow(AdapterNotFoundException::new);

        return adapter.handle(request, response, handler);
    }
}
