package slipp.controller;

import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import nextstep.web.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.support.db.DataBase;

import java.util.Map;

@Controller
public class CreateUserController {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    public String signup(@RequestParam Map<String, String[]> paramMap) throws Exception {
        User user = new User(paramMap.get("userId")[0],
                paramMap.get("password")[0],
                paramMap.get("name")[0],
                paramMap.get("email")[0]);
        log.debug("User : {}", user);

        DataBase.addUser(user);
        return "redirect:/";
    }
}
