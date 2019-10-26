package slipp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.view.JsonView;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_CREATED;

@nextstep.web.annotation.Controller
public class CreateUserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        User user = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"),
                req.getParameter("email"));
        log.debug("User : {}", user);

        DataBase.addUser(user);
        return "redirect:/";
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView execute2(HttpServletRequest req, HttpServletResponse resp) {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = null;
        try {
            user = objectMapper.readValue(req.getReader().readLine(), User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.debug("User : {}", user);

        DataBase.addUser(user);

        ModelAndView model = new ModelAndView(new JsonView());
        resp.setStatus(SC_CREATED);
        resp.setHeader("location", "/api/users?userId=" + user.getUserId());
        return model;
    }
}
