package slipp.controller;

import nextstep.web.annotation.Controller;
import nextstep.web.annotation.ModelAttribute;
import nextstep.web.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.dto.UserCreatedDto;
import slipp.support.db.DataBase;

import static nextstep.web.annotation.RequestMethod.GET;
import static nextstep.web.annotation.RequestMethod.POST;

@Controller
public class CreateUserController {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @RequestMapping(value = "/users/form", method = GET)
    public String read() {
        return "/user/form.jsp";
    }

    @RequestMapping(value = "/users/create", method = POST)
    public String signup(@ModelAttribute UserCreatedDto userCreatedDto) {
        User user = new User(userCreatedDto.getUserId(),
                userCreatedDto.getPassword(),
                userCreatedDto.getName(),
                userCreatedDto.getEmail());

        log.debug("User : {}", user);

        DataBase.addUser(user);
        return "redirect:/";
    }
}
