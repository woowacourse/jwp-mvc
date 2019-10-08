package slipp.controller;

import nextstep.mvc.tobe.view.JsonView;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.utils.JsonUtils;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.apache.commons.io.IOUtils;
import slipp.dto.UserCreatedDto;
import slipp.dto.UserUpdatedDto;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserRestController {

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        UserCreatedDto userCreatedDto = JsonUtils.toObject(IOUtils.toString(req.getReader()), UserCreatedDto.class);
        DataBase.addUser(userCreatedDto.toUser());

        return new ModelAndView(new JsonView().createOK("/api/users?userId=" + userCreatedDto.toUser().getUserId()));
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView find(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ModelAndView modelAndView = new ModelAndView(new JsonView());
        modelAndView.addObject("user", DataBase.findUserById(req.getParameter("userId")));

        return modelAndView;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView update(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        UserUpdatedDto userUpdatedDto = JsonUtils.toObject(IOUtils.toString(req.getReader()), UserUpdatedDto.class);
        DataBase.addUser(userUpdatedDto.toUser(req.getParameter("userId")));

        return new ModelAndView(new JsonView());
    }
}
