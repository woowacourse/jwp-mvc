package nextstep.mvc.tobe.argumentresolver;

import nextstep.mvc.tobe.exception.ArgumentResolveException;
import nextstep.utils.ClassUtils;
import org.reflections.Reflections;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.stream.Collectors;

public class ArgumentResolver {
    public List<Argument> arguments;

    public ArgumentResolver() {
        arguments = init();
    }

    private List<Argument> init() {
        return new Reflections(this.getClass().getPackage().getName())
                .getSubTypesOf(Argument.class)
                .stream()
                .map(clazz -> (Argument) ClassUtils.createInstance(clazz))
                .collect(Collectors.toList());
    }

    public Argument resolveParam(Parameter parameter) {
        return arguments.stream()
                .filter(argument -> argument.isMapping(parameter))
                .findAny()
                .orElseThrow(() -> new ArgumentResolveException("요청하신 파라미터에 대한 resolver를 찾을 수 없습니다."));
    }

    public List<Argument> getArguments() {
        return arguments;
    }
}
