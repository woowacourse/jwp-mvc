package nextstep.mvc.tobe.handler;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.view.returntyperesolver.ViewReturnTypeResolver;
import nextstep.mvc.tobe.view.returntyperesolver.ViewReturnTypeResolverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerExecution {
    private static final Logger logger = LoggerFactory.getLogger(HandlerExecution.class);

    private Object target;
    private Method method;

    public HandlerExecution(Object target, Method method) {
        this.target = target;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object handlerResult = method.invoke(target, request, response);
        ViewReturnTypeResolver viewReturnTypeResolver = ViewReturnTypeResolverManager.getViewReturnTypeResolver(handlerResult);

        return viewReturnTypeResolver.resolve(handlerResult);
    }
}
