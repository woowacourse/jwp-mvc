package nextstep.mvc.tobe;

import nextstep.mvc.tobe.argument.Argument;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ArgumentResolver {
    private final ParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
    private List<Argument> arguments = new ArrayList<>();

    public ArgumentResolver(Method method) {
        mappingArgumentNameType(method)
            .forEach((key, value) -> arguments.add(ArgumentFactory.of(key, value)));
    }

    private Map<String, Class> mappingArgumentNameType(Method method) {
        String[] names = nameDiscoverer.getParameterNames(method);
        Class[] types = method.getParameterTypes();
        Map<String, Class> argumentsNameTypeMap = new LinkedHashMap<>();
        for (int i = 0; i < names.length; i++) {
            argumentsNameTypeMap.put(names[i], types[i]);
        }
        return argumentsNameTypeMap;
    }

    public Object[] getArguments(HttpServletRequest request, HttpServletResponse response) {
        return arguments.stream()
            .map(argument -> argument.getArgument(request, response))
            .toArray()
            ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArgumentResolver that = (ArgumentResolver) o;

        return Objects.equals(arguments, that.arguments);
    }

    @Override
    public int hashCode() {
        return arguments != null ? arguments.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ArgumentResolver{" +
            "arguments=" + arguments +
            '}';
    }
}
