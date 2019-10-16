
package slipp.controller;

import nextstep.mvc.asis.Controller;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class ProfileController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        return Optional.ofNullable(
                DataBase.findUserById(req.getParameter("userId"))
        ).map(user -> {
            req.setAttribute("user", user);
            return "/user/profile.jsp";
        }).orElseThrow(() -> new NullPointerException("사용자를 찾을 수 없습니다."));
    }
}