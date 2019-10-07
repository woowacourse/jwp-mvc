package nextstep.mvc.tobe.view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ServletContextTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import nextstep.mvc.tobe.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class HandlebarsView implements View {

    private final String viewName;

    public HandlebarsView(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TemplateLoader loader = new ServletContextTemplateLoader(request.getServletContext());
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        response.getWriter().print(handlebars.compile(viewName).apply(model));
    }
}
