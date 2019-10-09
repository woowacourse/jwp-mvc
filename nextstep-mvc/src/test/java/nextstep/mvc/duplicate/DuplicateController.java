package nextstep.mvc.duplicate;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class DuplicateController {
    @RequestMapping(value = "/duplicate", method = RequestMethod.GET)
    public ModelAndView test1(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @RequestMapping(value = "/duplicate", method = RequestMethod.GET)
    public ModelAndView test2(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
