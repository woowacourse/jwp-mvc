package nextstep.mvc.tobe.view;

import nextstep.mvc.tobe.exception.JsonViewRenderException;
import nextstep.utils.JsonUtils;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class JsonView implements View {

    private static final int BOUNDARY_SIZE_OF_VALUES = 1;

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        try {
            PrintWriter out = response.getWriter();
            printModel(out, model);

            out.flush();
        } catch (IOException e) {
            throw new JsonViewRenderException(e);
        }
    }

    private void printModel(final PrintWriter out, final Map<String, ?> model) {
        if (model.size() == BOUNDARY_SIZE_OF_VALUES) {
            Object value = model.values().toArray()[0];
            out.print(JsonUtils.toJson(value));
        }
        if (model.size() > BOUNDARY_SIZE_OF_VALUES) {
            out.print(JsonUtils.toJson(model));
        }
    }
}
