package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ErrorView implements View {
    private static final int PAGE_NOT_FOUND = 404;

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(PAGE_NOT_FOUND);
    }
}
