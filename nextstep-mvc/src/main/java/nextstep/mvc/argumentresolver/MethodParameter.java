package nextstep.mvc.argumentresolver;

import nextstep.mvc.exception.NotFoundParameterNameException;
import nextstep.web.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodParameter {
    private final Method method;
    private final Parameter parameter;
    private final String name;

    public MethodParameter(Method method, Parameter parameter, String name) {
        this.method = method;
        this.parameter = parameter;
        this.name = name;
    }

    public Class<?> getType() {
        return parameter.getType();
    }

    public boolean hasNoDeclaredAnnotation() {
        return parameter.getDeclaredAnnotations().length == 0;
    }

    public boolean isAnnotationPresent(Class<? extends Annotation> annotation) {
        return parameter.isAnnotationPresent(annotation);
    }

    public String getParameterName() {
        return name;
    }

    public <T extends Annotation> T getDeclaredAnnotation(Class<T> annotation) {
        return parameter.getDeclaredAnnotation(annotation);
    }

    public String getPathVariable(String parameterName, String requestUrl) {
        RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);
        Pattern pattern = Pattern.compile(requestMapping.value()
                .replace("{" + parameterName + "}", "([a-zA-Z0-9])")
                .replaceAll("\\{[a-zA-Z0-9]+\\}", "[a-zA-Z0-9]+"));
        Matcher matcher = pattern.matcher(requestUrl);
        if(matcher.find()) {
            return matcher.group(1);
        }
        throw new NotFoundParameterNameException();
    }
}
