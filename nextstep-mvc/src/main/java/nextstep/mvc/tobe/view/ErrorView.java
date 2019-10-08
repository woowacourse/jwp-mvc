package nextstep.mvc.tobe.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ErrorView implements View {
    private final int status;

    public ErrorView(int status) {
        this.status = status;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(status);
    }
}
