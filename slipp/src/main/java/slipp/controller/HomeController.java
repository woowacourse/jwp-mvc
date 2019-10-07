package slipp.controller;

import nextstep.mvc.asis.Controller;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class HomeController implements Controller {

    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("users", DataBase.findAll());
        return "home.jsp";
    }
}
