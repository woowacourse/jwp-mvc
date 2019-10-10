package slipp.controller2;


import nextstep.mvc.tobe.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.PathVariable;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.dto.UserCreatedDto;
import slipp.dto.UserUpdatedDto;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/users/form", method = RequestMethod.GET)
    public String createForm() {
        return "/user/form";
    }

    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    public String create(UserCreatedDto userCreatedDto) {
        logger.debug("UserCreatedDto : {}", userCreatedDto);

        final User user = userCreatedDto.toEntity();
        DataBase.addUser(user);

        return "redirect:/";
    }

    @RequestMapping(value = "/users/profile", method = RequestMethod.GET)
    public ModelAndView show(@PathVariable String userId) {
        User user = DataBase.findUserById(userId);
        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }
        return new ModelAndView("/user/profile").addObject("user", user);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView list(HttpServletRequest req) throws Exception {
        final Collection<User> users = DataBase.findAll();
        return new ModelAndView("/user/list").addObject("users", users);
    }

    @RequestMapping(value = "/users/updateForm", method = RequestMethod.GET)
    public ModelAndView updateForm(HttpServletRequest req, String userId) throws Exception {
        User user = DataBase.findUserById(userId);
        if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }
        return new ModelAndView("/user/updateForm").addObject("user", user);
    }

    @RequestMapping(value = "/users/update", method = RequestMethod.PUT)
    public String update(@PathVariable String userId,
                         UserUpdatedDto userUpdatedDto,
                         HttpServletRequest req) {
        final User user = DataBase.findUserById(userId);

        if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }

        final User updateUser = userUpdatedDto.toEntity();
        user.update(updateUser);
        logger.debug("Update User : {}", updateUser);
        return "redirect:/";
    }
}
