package nextstep.mvc.handlermapping;

import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HandlerKeyFactory {
    private static final Logger log = LoggerFactory.getLogger(HandlerKeyFactory.class);

    private HandlerKeyFactory() {
    }

    private static class SingletonHolder {
        private static final HandlerKeyFactory INSTANCE = new HandlerKeyFactory();
    }

    public static HandlerKeyFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public List<HandlerKey> fromMethod(Method method) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if (requestMapping == null) {
            return Collections.emptyList();
        }

        String url = requestMapping.value();
        if (url.isEmpty()) {
            return Collections.emptyList();
        }

        return getMethods(requestMapping).stream()
                .map(requestMethod -> HandlerKey.fromUrlAndRequestMethod(url, requestMethod))
                .collect(Collectors.toList());
    }

    private List<RequestMethod> getMethods(RequestMapping requestMapping) {
        List<RequestMethod> methods = Arrays.asList(requestMapping.method());

        return methods.isEmpty() ? Arrays.asList(RequestMethod.values()) : methods;
    }
}
