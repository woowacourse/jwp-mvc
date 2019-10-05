package nextstep.mvc.tobe;

import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class TextView implements View {

    private final String content;

    public TextView(String content) {
        this.content = content;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(MediaType.PLAIN_TEXT);
        response.getWriter().print(content);
    }
}
