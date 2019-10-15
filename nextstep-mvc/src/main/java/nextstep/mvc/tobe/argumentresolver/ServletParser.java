package nextstep.mvc.tobe.argumentresolver;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.exception.NotMatchParserTypeException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.function.BiFunction;

public enum ServletParser {
    REQUEST(HttpServletRequest.class, (request, response) -> request),
    RESPONSE(HttpServletResponse.class, (request, response) -> response),
    SESSION(HttpSession.class, (request, response) -> request.getSession()),
    MODEL_AND_VIEW(ModelAndView.class, (request, response) -> new ModelAndView());

    private final Class<?> type;
    private final BiFunction<HttpServletRequest, HttpServletResponse, Object> parser;

    ServletParser(Class<?> type, BiFunction<HttpServletRequest, HttpServletResponse, Object> parser) {
        this.type = type;
        this.parser = parser;
    }

    public static boolean supports(Class<?> type) {
        return Arrays.stream(values())
                .anyMatch(value -> value.type.isAssignableFrom(type));
    }

    public static ServletParser findParser(Class<?> type) {
        return Arrays.stream(values())
                .filter(value -> value.type.isAssignableFrom(type))
                .findAny()
                .orElseThrow(NotMatchParserTypeException::new);
    }

    public Object parse(HttpServletRequest request, HttpServletResponse response) {
        return parser.apply(request, response);
    }
}
