package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JspView implements View {
    private static final String SUFFIX = ".jsp";

    private final String view;

    public JspView(final String view) {
        if (view.endsWith(SUFFIX)) {
            this.view = view;
            return;
        }
        this.view = view + SUFFIX;
    }

    @Override
    public void render(final Map<String, ?> model, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        request.getRequestDispatcher(this.view).forward(request, response);
    }
}
