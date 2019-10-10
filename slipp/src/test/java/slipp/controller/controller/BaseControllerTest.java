package slipp.controller.controller;

import nextstep.mvc.tobe.handler.HandlerExecution;
import nextstep.mvc.tobe.handlerresolver.AnnotationHandlerMapping;
import nextstep.mvc.tobe.handlerresolver.HandlerMappingAdapter;
import nextstep.mvc.tobe.handlerresolver.HandlerResolver;
import nextstep.mvc.tobe.view.viewresolver.JsonViewResolver;
import nextstep.mvc.tobe.view.viewresolver.JspViewResolver;
import nextstep.mvc.tobe.view.viewresolver.RedirectViewResolver;
import nextstep.mvc.tobe.view.viewresolver.ViewResolver;
import slipp.ManualHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class BaseControllerTest {
    public List<HandlerResolver> handlerAdapters = new ArrayList<>();
    public List<ViewResolver> viewResolvers = new ArrayList<>();

    public void init() {
        handlerAdapters.add(new AnnotationHandlerMapping("slipp.controller"));
        handlerAdapters.add(new HandlerMappingAdapter(new ManualHandlerMapping()));
        handlerAdapters.forEach(HandlerResolver::initialize);
        viewResolvers.add(new JspViewResolver());
        viewResolvers.add(new JsonViewResolver());
        viewResolvers.add(new RedirectViewResolver());
    }

    public HandlerExecution mappingHandler(HttpServletRequest req, HttpServletResponse resp) {
        return handlerAdapters.stream().filter(adapter -> adapter.support(req, resp))
                .findFirst()
                .orElseThrow(IllegalAccessError::new)
                .getHandler(req)
                ;
    }
}
