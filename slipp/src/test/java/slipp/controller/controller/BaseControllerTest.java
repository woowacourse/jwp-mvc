package slipp.controller.controller;

import nextstep.mvc.tobe.adapter.ControllerAdapter;
import nextstep.mvc.tobe.adapter.HandlerAdapter;
import nextstep.mvc.tobe.adapter.HandlerExecutionAdapter;
import nextstep.mvc.tobe.adapter.NoSuchAdapterException;
import nextstep.mvc.tobe.handler.AnnotationHandlerMapping;
import nextstep.mvc.tobe.handler.HandlerMapping;
import nextstep.mvc.tobe.handler.NoSuchHandlerException;
import slipp.ManualHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


public class BaseControllerTest {
    public List<HandlerMapping> handlerMappings = new ArrayList<>();
    public List<HandlerAdapter> handlerAdapters = new ArrayList<>();

    public void init() {
        handlerMappings.add(new AnnotationHandlerMapping("slipp.controller"));
        handlerMappings.add(new ManualHandlerMapping());
        handlerMappings.forEach(HandlerMapping::initialize);

        handlerAdapters.add(new HandlerExecutionAdapter());
        handlerAdapters.add(new ControllerAdapter());
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
