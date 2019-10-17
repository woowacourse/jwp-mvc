package slipp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.view.JsonView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Controller
public class UserApiController {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest req, HttpServletResponse res) throws IOException {
        final User user = objectMapper.readValue(req.getInputStream(), User.class);
        DataBase.addUser(user);
        res.setStatus(HttpServletResponse.SC_CREATED);
        res.setHeader("Location", "/api/users?userId=" + user.getUserId());
        return new ModelAndView(new JsonView());
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView read(HttpServletRequest req, HttpServletResponse res) {
        res.setStatus(HttpServletResponse.SC_OK);
        return new ModelAndView(new JsonView()).addObject("user", DataBase.findUserById(req.getParameter("userId")));
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView update(HttpServletRequest req, HttpServletResponse res) throws IOException {
        final User toUpdate = objectMapper.readValue(req.getInputStream(), User.class);
        Optional.ofNullable(DataBase.findUserById(req.getParameter("userId"))).ifPresent(user -> {
            user.update(toUpdate);
            DataBase.addUser(user);
        });
        res.setStatus(HttpServletResponse.SC_OK);
        return new ModelAndView(new JsonView());
    }
}