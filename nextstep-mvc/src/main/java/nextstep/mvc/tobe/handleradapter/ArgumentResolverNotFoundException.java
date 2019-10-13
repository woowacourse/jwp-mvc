package nextstep.mvc.tobe.handleradapter;

import nextstep.mvc.HttpServletRequestException;
import nextstep.web.support.HttpStatus;

public class ArgumentResolverNotFoundException extends HttpServletRequestException {
    public ArgumentResolverNotFoundException() {
        super("Parameter에 대한 Argument Resolver를 찾지 못했습니다.", HttpStatus.NOT_FOUND);
    }
}
