package slipp.tobe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.RedirectView;
import nextstep.mvc.tobe.View;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.springframework.http.HttpStatus;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UserRestController {
    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public View signup(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(req.getReader(), User.class);
        DataBase.addUser(user);
        return new RedirectView("/api/users?userId=" + user.getUserId(), HttpStatus.CREATED.value());
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public User read(HttpServletRequest req, HttpServletResponse resp) {
        return DataBase.findUserById(req.getParameter("userId"));
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public Object update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(req.getReader(), User.class);
        User foundUser = DataBase.findUserById(req.getParameter("userId"));
        foundUser.update(user);
        resp.setStatus(HttpStatus.OK.value());
        return null;
    }
}
