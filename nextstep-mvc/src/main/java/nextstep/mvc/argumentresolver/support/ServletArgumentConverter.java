package nextstep.mvc.argumentresolver.support;

import nextstep.mvc.exception.ServletArgumentTypeNotSupportedException;
import nextstep.mvc.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.function.BiFunction;

public enum ServletArgumentConverter {
    HTTP_REQUEST(HttpServletRequest.class, (req, res) -> req),
    HTTP_RESPONSE(HttpServletResponse.class, (req, res) -> res),
    HTTP_SESSION(HttpSession.class, (req, res) -> req.getSession()),
    MODEL_AND_VIEW(ModelAndView.class, (req, res) -> new ModelAndView());

    private final Class<?> valueType;
    private final BiFunction<HttpServletRequest, HttpServletResponse, Object> convertFunction;

    ServletArgumentConverter(Class<?> valueType, BiFunction<HttpServletRequest, HttpServletResponse, Object> convertFunction) {
        this.valueType = valueType;
        this.convertFunction = convertFunction;
    }

    public static boolean supports(Class<?> valueType) {
        return Arrays.stream(values())
                .anyMatch(argument -> argument.valueType.equals(valueType));
    }

    public static ServletArgumentConverter getServletArgumentConverter(Class<?> valueType) {
        return Arrays.stream(values())
                .filter(argument -> argument.valueType.equals(valueType))
                .findAny()
                .orElseThrow(ServletArgumentTypeNotSupportedException::new);
    }

    public Object convert(HttpServletRequest request, HttpServletResponse response) {
        return convertFunction.apply(request, response);
    }
}