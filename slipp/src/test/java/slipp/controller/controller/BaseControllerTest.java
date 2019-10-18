package slipp.controller.controller;

import nextstep.mvc.tobe.handler.AnnotationHandlerMapping;
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
    public List<HandlerResolver> handlerResolvers = new ArrayList<>();
    public List<ViewResolver> viewResolvers = new ArrayList<>();

    public void init() {
        handlerResolvers.add(new AnnotationHandlerMapping("slipp.controller"));
        handlerResolvers.add(new HandlerMappingAdapter(new ManualHandlerMapping()));
        handlerResolvers.forEach(HandlerResolver::initialize);
        viewResolvers.add(new JspViewResolver());
        viewResolvers.add(new JsonViewResolver());
        viewResolvers.add(new RedirectViewResolver());
    }

    public HandlerExecution2 mappingHandler(HttpServletRequest req, HttpServletResponse resp) {
        return handlerResolvers.stream().filter(resolver -> resolver.support(req, resp))
                .findFirst()
                .orElseThrow(IllegalAccessError::new)
                .getHandler(req)
                ;
    }
}
