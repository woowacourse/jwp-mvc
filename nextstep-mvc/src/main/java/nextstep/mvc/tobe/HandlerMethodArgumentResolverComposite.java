package nextstep.mvc.tobe;

import nextstep.web.annotation.PathVariable;
import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.stream.Stream;

// TODO 역할별로 클래스 분리하기, 재사용 가능하게 하기
public class HandlerMethodArgumentResolverComposite implements HandlerMethodArgumentResolver {
    public Object resolveArgument(final HttpServletRequest request, final MethodParameter methodParameter) {
        final Parameter parameter = methodParameter.getParameter();
        final String name = methodParameter.getName();
        // string
        final String value = request.getParameter(name);
        final Class<?> type = parameter.getType();

        // String
        if (type.equals(String.class)) {
            return value;
        }


        // PathVariable
        if (parameter.isAnnotationPresent(PathVariable.class)) {
            final String uri = request.getRequestURI();
            final PathPattern pp = parse("/users/{id}");
            final Map<String, String> uriVariables = pp
                    .matchAndExtract(toPathContainer(uri))
                    .getUriVariables();

            return Long.parseLong(uriVariables.get(name));
        }

        // 기본타입
        // todo 다른 타입도 해주기.. or 예외처리
        if (type.isPrimitive()) {
            if (type.equals(int.class)) {
                return Integer.parseInt(value);
            } else if (type.equals(long.class)) {
                return Long.parseLong(value);
            }
        }


        // java bean  생성자 & field
        // todo 기본 생성자 + field로 생성
        try {
            final Object[] objects = Stream.of(type.getDeclaredFields())
                    .map(field -> {
                        final String fieldValue = request.getParameter(field.getName());

                        if (field.getType().isPrimitive()) {
                            if (field.getType().equals(int.class)) {
                                return Integer.parseInt(fieldValue);
                            } else if (field.getType().equals(long.class)) {
                                return Long.parseLong(fieldValue);
                            }
                        }

                        return fieldValue;
                    })
                    .toArray();
            final Constructor<?>[] constructors = type.getConstructors();
            return constructors[constructors.length - 1].newInstance(objects);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return value;
    }

    private PathPattern parse(String path) {
        PathPatternParser pp = new PathPatternParser();
        pp.setMatchOptionalTrailingSeparator(true);
        return pp.parse(path);
    }

    private static PathContainer toPathContainer(String path) {
        if (path == null) {
            return null;
        }
        return PathContainer.parsePath(path);
    }
}
