package nextstep.mvc.tobe.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class EmptyView implements View {
    private static final String EMPTY = "";
    private final String viewName;

    public EmptyView() {
        viewName = EMPTY;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) {
    }
}
