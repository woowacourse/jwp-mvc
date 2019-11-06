package nextstep.mvc.handler.handlermapping;

import nextstep.mvc.exception.HandlerKeyUrlNotExistException;
import nextstep.mvc.exception.MethodNotAnnotatedException;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class HandlerKeyFactory {
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
        RequestMapping requestMapping = Optional.ofNullable(method.getAnnotation(RequestMapping.class))
                .orElseThrow(() -> MethodNotAnnotatedException.from(method, RequestMapping.class));

        String url = getUrl(requestMapping);
        List<RequestMethod> requestMethods = getMethods(requestMapping);

        return createHandlerKeys(url, requestMethods);
    }

    private String getUrl(RequestMapping requestMapping) {
        String url = requestMapping.value();
        if (url.isEmpty()) {
            throw new HandlerKeyUrlNotExistException();
        }
        return url;
    }

    private List<RequestMethod> getMethods(RequestMapping requestMapping) {
        List<RequestMethod> methods = Arrays.asList(requestMapping.method());

        return methods.isEmpty() ? Arrays.asList(RequestMethod.values()) : methods;
    }

    private List<HandlerKey> createHandlerKeys(String url, List<RequestMethod> requestMethods) {
        return requestMethods.stream()
                .map(requestMethod -> HandlerKey.fromUrlAndRequestMethod(url, requestMethod))
                .collect(Collectors.toList());
    }
}
