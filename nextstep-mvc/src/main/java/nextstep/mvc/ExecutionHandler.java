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
    private static Map<Class<?>, Function<Object, ModelAndView>> converters;

    static {
        converters = new HashMap<>();
        converters.put(ModelAndView.class, o -> (ModelAndView) o);
        converters.put(String.class, o -> convertString((String) o));
    }

    public static ModelAndView handle(Execution execution,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        if (execution == null) {
            return new ModelAndView(new JspView("/err/404.jsp"));
        }

        Object result = execution.execute(request, response);
        Function<Object, ModelAndView> converter = converters.get(result.getClass());
        if (converter == null) {
            throw new ExecutionHandleException("처리할 수 없는 형태입니다.");
        }
        return converter.apply(result);
    }

    private static ModelAndView convertString(String result) {
        return new ModelAndView(ViewNameResolver.resolve(result));
    }
}
