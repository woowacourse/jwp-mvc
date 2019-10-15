package slipp.controller;

import nextstep.mvc.view.JspView;
import nextstep.mvc.view.View;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    private static final String USERS = "users";

    @RequestMapping("/")
    public View home(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute(USERS, DataBase.findAll());
        return new JspView("home.jsp");
    }
}
