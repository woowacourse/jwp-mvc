package nextstep.mvc.handler.handleradapter;

import nextstep.mvc.exception.BadHttpRequestException;
import nextstep.mvc.exception.NotSupportedHandlerException;
import nextstep.mvc.handler.Handler;
import nextstep.mvc.handler.HandlerFactory;
import nextstep.mvc.handler.handlermapping.HandlerMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class SupportedHandlerFactory implements HandlerFactory {
    private static final Logger log = LoggerFactory.getLogger(SupportedHandlerFactory.class);

    private final HandlerMapping mapping;
    private final HandlerAdapterWrappers wrappers;

    public SupportedHandlerFactory(HandlerMapping mapping, HandlerAdapterWrappers wrappers) {
        this.mapping = mapping;
        this.wrappers = wrappers;
    }

    public static SupportedHandlerFactory from(HandlerMapping mapping, HandlerAdapterWrappers wrappers) {
        return new SupportedHandlerFactory(mapping, wrappers);
    }

    @Override
    public Handler create(HttpServletRequest request) {
        Object handler = mapping.getHandler(request).orElseThrow(() -> BadHttpRequestException.from(request));

        return wrappers.wrap(handler)
                .orElseThrow(() -> NotSupportedHandlerException.ofHandler(handler));
    }
}
