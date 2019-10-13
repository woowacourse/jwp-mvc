package nextstep.mvc.tobe;

import nextstep.mvc.tobe.argument.Argument;
import nextstep.mvc.tobe.argument.ObjectArgument;
import nextstep.mvc.tobe.argument.ParameterIntArgument;
import nextstep.mvc.tobe.argument.ParameterLongArgument;
import nextstep.mvc.tobe.argument.ParameterStringArgument;
import nextstep.mvc.tobe.argument.RequestArgument;
import nextstep.mvc.tobe.argument.ResponseArgument;
import nextstep.mvc.tobe.argument.SessionArgument;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ArgumentFactory {
    private static final Map<Class, Function<String, Argument>> factory;

    static {
        factory = new HashMap<>();
        factory.put(String.class, ParameterStringArgument::new);
        factory.put(int.class, ParameterIntArgument::new);
        factory.put(long.class, ParameterLongArgument::new);
        factory.put(HttpServletRequest.class, noUsed -> new RequestArgument());
        factory.put(HttpServletResponse.class, noUsed -> new ResponseArgument());
        factory.put(HttpSession.class, noUsed -> new SessionArgument());
    }

    private ArgumentFactory() {
    }

    public static Argument of(String name, Class type) {
        return factory.getOrDefault(type, noUsed -> new ObjectArgument(type)).apply(name);
    }
}
