package nextstep.mvc.tobe.controllermapper;

import nextstep.mvc.tobe.controllermapper.adepter.MethodParameter;
import nextstep.mvc.tobe.controllermapper.adepter.ParameterAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

public class ControllerParameterMapper {
    private static final Logger log = LoggerFactory.getLogger(ControllerParameterMapper.class);

    private List<ParameterAdapter> adepters;
    private final Method method;

    public ControllerParameterMapper(Method method, List<ParameterAdapter> adepters) {
        this.method = method;
        this.adepters = adepters;
    }

    public Object[] getObjects(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int parameterCount = method.getParameterCount();
        Object[] objects = new Object[parameterCount];

        for (int i = 0; i < parameterCount; i++) {
            MethodParameter methodParameter = new MethodParameter(method, i);
            objects[i] = adepters.stream()
                    .filter(adepter -> adepter.supports(methodParameter))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("지원하지 못하는 파라미터 입니다."))
                    .cast(request, response, methodParameter);
        }
        return objects;
    }
}
