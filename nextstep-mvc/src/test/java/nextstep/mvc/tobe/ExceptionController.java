package nextstep.mvc.tobe;

import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ExceptionController {
    @RequestMapping(value = "/users")
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @RequestMapping(value = "/users")
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
