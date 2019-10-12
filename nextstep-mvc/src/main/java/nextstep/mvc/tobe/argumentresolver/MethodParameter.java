package nextstep.mvc.tobe.argumentresolver;

import java.lang.reflect.Parameter;

public class MethodParameter {
    private Parameter parameter;
    private String parameterName;

    public MethodParameter(Parameter parameter, String parameterName) {
        this.parameter = parameter;
        this.parameterName = parameterName;
    }

    public Class<?> getParameterType() {
        return parameter.getType();
    }

    public String getParameterName() {
        return parameterName;
    }
}
