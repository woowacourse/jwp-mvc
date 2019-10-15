package slipp.controller.tobe;

import nextstep.mvc.tobe.JspView;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public ModelAndView handle(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.setAttribute("users", DataBase.findAll());
        log.debug("Home Controller : {}", DataBase.findAll());
        return new ModelAndView(new JspView("home.jsp"));
    }
}
