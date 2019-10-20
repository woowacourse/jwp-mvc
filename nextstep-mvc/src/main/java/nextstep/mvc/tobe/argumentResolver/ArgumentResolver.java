package nextstep.mvc.tobe.argumentResolver;

import nextstep.mvc.tobe.NotMatchParameterException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class ArgumentResolver {
    private final List<ArgumentResolverAdapter> adapters;

    public ArgumentResolver(HttpServletRequest request, HttpServletResponse response) {
        List<ArgumentResolverAdapter> adapters = new ArrayList<>();

        adapters.add(new RequestResolverAdapter(request));
        adapters.add(new RequestParamResolverAdapter(request));
        adapters.add(new ResponseResolverAdapter(response));

        this.adapters = Collections.unmodifiableList(adapters);
    }

    public Object[] resolve(Method method) {
        List<Object> result = new LinkedList<>();
        Parameter[] parameters = method.getParameters();

        for (Parameter parameter : parameters) {
            Object matchParameter = findAdapter(parameter).orElseThrow(NotMatchParameterException::new);
            result.add(matchParameter);
        }

        return result.toArray();
    }

    private Optional<Object> findAdapter(Parameter parameter) {
        return adapters.stream()
                .filter(adapter -> adapter.match(parameter))
                .map(adapter -> adapter.get(parameter))
                .findFirst();
    }
}
