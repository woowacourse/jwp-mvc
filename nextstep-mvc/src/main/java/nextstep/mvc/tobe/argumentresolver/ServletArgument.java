package nextstep.mvc.tobe.argumentresolver;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.argumentresolver.exception.NotSupportedServletArgumentTypeException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.function.BiFunction;

public enum ServletArgument {
    HTTP_SERVLET_REQUEST(HttpServletRequest.class, (req, res) -> req),
    HTTP_SERVLET_RESPONSE(HttpServletResponse.class, (req, res) -> res),
    HTTP_SESSION(HttpSession.class, (req, res) -> req.getSession()),
    MODEL_AND_VIEW(ModelAndView.class, (req, res) -> new ModelAndView());

    private final Class<?> clazz;
    private final BiFunction<HttpServletRequest, HttpServletResponse, Object> resolver;

    ServletArgument(Class<?> clazz, BiFunction<HttpServletRequest, HttpServletResponse, Object> resolver) {
        this.clazz = clazz;
        this.resolver = resolver;
    }

    static boolean supports(Class<?> type) {
        return Arrays.stream(values())
                .anyMatch(servletArgument -> servletArgument.assignable(type));
    }

    public static ServletArgument getResolver(Class<?> type) {
        return Arrays.stream(values())
                .filter(servletArgument -> servletArgument.assignable(type))
                .findAny()
                .orElseThrow(NotSupportedServletArgumentTypeException::new);
    }

    private boolean assignable(Class<?> type) {
        return clazz.isAssignableFrom(type);
    }

    public Object resolve(HttpServletRequest request, HttpServletResponse response) {
        return resolver.apply(request, response);
    }
}
