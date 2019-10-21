package nextstep.mvc.mock;

import nextstep.web.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ArgumentResolverTestHelper {
    public void servletArgumentsMethod(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {

    }

    public void modelAttributeArgumentsMethod(@ModelAttribute StringTestUser user, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {

    }
}
