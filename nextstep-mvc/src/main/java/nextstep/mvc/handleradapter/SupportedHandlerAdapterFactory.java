package nextstep.mvc.handleradapter;

import nextstep.mvc.BadHttpRequestException;
import nextstep.mvc.NotSupportedHandlerException;
import nextstep.mvc.handlermapping.HandlerMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class SupportedHandlerAdapterFactory implements HandlerAdapterFactory {
    private static final Logger log = LoggerFactory.getLogger(SupportedHandlerAdapterFactory.class);

    private final HandlerMapping mapping;
    private final HandlerAdapterWrappers wrappers;

    public SupportedHandlerAdapterFactory(HandlerMapping mapping, HandlerAdapterWrappers wrappers) {
        this.mapping = mapping;
        this.wrappers = wrappers;
    }

    public static SupportedHandlerAdapterFactory from(HandlerMapping mapping, HandlerAdapterWrappers wrappers) {
        return new SupportedHandlerAdapterFactory(mapping, wrappers);
    }

    @Override
    public Handler create(HttpServletRequest request) {
        Object handler = mapping.getHandler(request).orElseThrow(() -> BadHttpRequestException.from(request));

        return wrappers.wrap(handler)
                .orElseThrow(() -> NotSupportedHandlerException.ofHandler(handler));
    }
}
