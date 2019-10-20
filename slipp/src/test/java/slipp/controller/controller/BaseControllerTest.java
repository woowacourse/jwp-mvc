package slipp.controller.controller;

import nextstep.mvc.tobe.adapter.HandlerAdapter;
import nextstep.mvc.tobe.adapter.HandlerExecutionAdapter;
import nextstep.mvc.tobe.adapter.NoSuchAdapterException;
import nextstep.mvc.tobe.handler.AnnotationHandlerMapping;
import nextstep.mvc.tobe.handler.HandlerMapping;
import nextstep.mvc.tobe.handler.NoSuchHandlerException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


public class BaseControllerTest {
    private List<HandlerMapping> handlerMappings = new ArrayList<>();
    private List<HandlerAdapter> handlerAdapters = new ArrayList<>();

    public void init() {
        handlerMappings.add(new AnnotationHandlerMapping("slipp.controller"));
        handlerMappings.forEach(HandlerMapping::initialize);

        handlerAdapters.add(new HandlerExecutionAdapter());
    }

    public Object mappingHandler(HttpServletRequest req, HttpServletResponse resp) {
        return handlerMappings.stream().filter(handler -> handler.support(req, resp))
                .findFirst()
                .orElseThrow(NoSuchHandlerException::new)
                .getHandler(req);
    }

    public HandlerAdapter mappingAdapter(Object handler) {
        return handlerAdapters.stream().filter(adapter -> adapter.support(handler))
                .findFirst()
                .orElseThrow(NoSuchAdapterException::new);
    }
}
