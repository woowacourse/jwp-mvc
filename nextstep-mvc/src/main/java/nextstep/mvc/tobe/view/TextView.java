package nextstep.mvc.tobe.view;

import nextstep.mvc.tobe.View;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class TextView implements View {

    private static final TextView EMPTY_VIEW = new TextView("");

    private final String content;

    public TextView(String content) {
        this.content = content;
    }

    public static final TextView emptyView() {
        return EMPTY_VIEW;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(MediaType.PLAIN_TEXT);
        response.getWriter().print(content);
    }
}
