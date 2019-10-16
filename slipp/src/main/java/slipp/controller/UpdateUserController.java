package slipp.controller;

import nextstep.mvc.asis.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class UpdateUserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(UpdateUserController.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        return Optional.ofNullable(
                DataBase.findUserById(req.getParameter("userId"))
        ).filter(user -> UserSessionUtils.isSameUser(req.getSession(), user))
        .map(user -> {
            final User updateUser = new User(
                    req.getParameter("userId"),
                    req.getParameter("password"),
                    req.getParameter("name"),
                    req.getParameter("email")
            );
            log.debug("Update User : {}", updateUser);
            user.update(updateUser);
            return "redirect:/";
        }).orElseThrow(() -> new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다."));
    }
}