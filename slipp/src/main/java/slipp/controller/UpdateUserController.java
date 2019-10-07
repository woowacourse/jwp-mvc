package slipp.controller;

import nextstep.mvc.tobe.view.RedirectView;
import nextstep.web.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.dto.UserUpdatedDto;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpSession;

import static nextstep.web.annotation.RequestMethod.PUT;

@Controller
public class UpdateUserController {
    private static final Logger log = LoggerFactory.getLogger(UpdateUserController.class);

    @RequestMapping(value = "/user/update", method = PUT)
    public RedirectView execute(@ModelAttribute UserUpdatedDto userUpdatedDto, @RequestParam String userId, HttpSession session) {
        User user = DataBase.findUserById(userId);
        if (!UserSessionUtils.isSameUser(session, user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }

        User updateUser = new User(userId,
                userUpdatedDto.getPassword(),
                userUpdatedDto.getName(),
                userUpdatedDto.getEmail());

        log.debug("Update User : {}", updateUser);
        user.update(updateUser);
        return new RedirectView("/");
    }
}
