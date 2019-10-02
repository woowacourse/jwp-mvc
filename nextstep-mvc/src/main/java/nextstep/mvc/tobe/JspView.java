package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JspView implements View {
    private final String path;

    public JspView(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

    }
}
