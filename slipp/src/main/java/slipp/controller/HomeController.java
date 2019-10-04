package slipp.controller;

import nextstep.mvc.asis.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@nextstep.web.annotation.Controller
public class HomeController implements Controller {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("users", DataBase.findAll());
        return "home.jsp";
    }
}
