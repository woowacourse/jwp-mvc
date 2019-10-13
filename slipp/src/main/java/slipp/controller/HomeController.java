package slipp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.support.db.DataBase;
import nextstep.mvc.asis.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        log.debug("previous home controller called.!!");
        req.setAttribute("users", DataBase.findAll());
        return "home.jsp";
    }
}
