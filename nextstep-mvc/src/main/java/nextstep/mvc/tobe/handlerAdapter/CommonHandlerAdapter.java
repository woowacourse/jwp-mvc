package nextstep.mvc.tobe.handlerAdapter;

import nextstep.mvc.tobe.HandlerExecution;

public class CommonHandlerAdapter implements HandlerAdapter{
    public boolean canAdapt(Object handler){
        return handler instanceof HandlerExecution;
    }
}

