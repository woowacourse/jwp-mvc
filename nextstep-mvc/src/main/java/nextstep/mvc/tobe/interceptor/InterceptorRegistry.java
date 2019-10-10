package nextstep.mvc.tobe.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InterceptorRegistry {
    private List<InterceptorRegistration> registrations = new ArrayList<>();

    public InterceptorRegistration addInterceptor(final HandlerInterceptor interceptor) {
        final InterceptorRegistration registration = InterceptorRegistration.from(interceptor);
        registrations.add(registration);
        return registration;
    }

    public List<HandlerInterceptor> getHandlerInterceptors(final String path){
        return registrations.stream()
                .filter(registration -> registration.match(path))
                .map(InterceptorRegistration::getInterceptor)
                .collect(Collectors.toList());
    }
}
