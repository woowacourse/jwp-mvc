package nextstep.mvc;

import nextstep.mvc.exception.ExecutionHandleException;
import nextstep.mvc.tobe.Execution;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.view.JspView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ExecutionHandler {
    private static Map<Class<?>, Function<Object, ModelAndView>> handlerMapping;

    static {
        handlerMapping = new HashMap<>();
        handlerMapping.put(String.class, o -> convertString((String) o));
        handlerMapping.put(ModelAndView.class, o -> (ModelAndView) o);
    }

    public static ModelAndView handle(Execution execution,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        if (execution == null) {
            return new ModelAndView(new JspView("/err/404.jsp"));
        }

        Object result = execution.execute(request, response);
        ModelAndView modelAndView = handlerMapping.get(result.getClass()).apply(request);
        if (modelAndView == null) {
            throw new ExecutionHandleException("처리할 수 없는 형태입니다.");
        }
        return modelAndView;
    }

    private static ModelAndView convertString(String result) {
        return new ModelAndView(ViewNameResolver.resolve(result));
    }
}
