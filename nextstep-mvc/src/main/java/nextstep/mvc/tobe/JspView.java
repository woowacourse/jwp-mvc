package nextstep.mvc.tobe;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JspView implements View {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";
    private static final String SUFFIX = ".jsp";

    private final String view;

    public JspView(String view) {
        this.view = view + SUFFIX;
    }

    public String getView() {
        return view;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (view.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            response.sendRedirect(view.substring(DEFAULT_REDIRECT_PREFIX.length()));
            return;
        }

        RequestDispatcher rd = request.getRequestDispatcher(view);
        rd.forward(request, response);
    }
}
