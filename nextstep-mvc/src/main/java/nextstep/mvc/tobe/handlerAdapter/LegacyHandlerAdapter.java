package nextstep.mvc.tobe.handlerAdapter;

import nextstep.mvc.asis.Controller;

public class LegacyHandlerAdapter implements HandlerAdapter {
    public boolean canAdapt(Object handler) {
        return handler instanceof Controller;
    }
}
