package nextstep.mvc.tobe.view;

import nextstep.mvc.tobe.View;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class JspView implements View {
    String name;

    public JspView(String name) {
        this.name = name;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        model.entrySet().stream()
                .forEach(entry -> req.setAttribute(entry.getKey(), entry.getValue()));
        req.getRequestDispatcher(name).forward(req, resp);
    }
}
