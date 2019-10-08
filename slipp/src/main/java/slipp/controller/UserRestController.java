package slipp.controller;

import nextstep.mvc.tobe.view.JsonView;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.utils.JsonUtils;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.dto.UserCreatedDto;
import slipp.dto.UserUpdatedDto;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserRestController {
    private static final Logger log = LoggerFactory.getLogger(UserRestController.class);

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        UserCreatedDto userCreatedDto = JsonUtils.toObject(IOUtils.toString(req.getReader()), UserCreatedDto.class);
        DataBase.addUser(userCreatedDto.toUser());

        resp.setStatus(201);
        resp.setHeader("location", "/api/users?userId=" + userCreatedDto.toUser().getUserId());
        return new ModelAndView(new JsonView());
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView find(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ModelAndView modelAndView = new ModelAndView(new JsonView());
        modelAndView.addObject("user", DataBase.findUserById(req.getParameter("userId")));
        resp.setStatus(200);
        return modelAndView;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView update(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        UserUpdatedDto userUpdatedDto = JsonUtils.toObject(IOUtils.toString(req.getReader()), UserUpdatedDto.class);
        DataBase.addUser(userUpdatedDto.toUser(req.getParameter("userId")));
        resp.setStatus(200);
        return new ModelAndView(new JsonView());
    }
}
