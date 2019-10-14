package nextstep.mvc.view;

import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class TextView implements View {
    private final String body;

    public TextView(String body) {
        this.body = body;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(MediaType.PLAIN_TEXT.getType());
        response.getWriter().print(body);
    }
}
