package nextstep.mvc.handler.handlermapping;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AnnotatedController {
    public AnnotatedController() {
    }

    @RequestMapping(value = "/hello", method = {RequestMethod.GET})
    public ModelAndView handler(HttpServletRequest request, HttpServletResponse response) {
        return HandlerExecutionFactoryTest.factoryModelAndView;
    }
}
