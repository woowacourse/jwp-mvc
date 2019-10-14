package slipp.controller;

import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String execute(HttpServletRequest req) throws Exception {
        req.setAttribute("users", DataBase.findAll());
        return "home.jsp";
    }
}
