package nextstep.mvc.asis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class ForwardController implements Controller {
    private final String forwardUrl;

    public ForwardController(String forwardUrl) {
        this.forwardUrl = Optional.ofNullable(forwardUrl).orElseThrow(() ->
            new NullPointerException("forwardUrl is null. 이동할 URL을 입력하세요.")
        );
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        return this.forwardUrl;
    }
}