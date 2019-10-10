package nextstep.mvc.tobe;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class JsonView implements View {
    private ObjectMapper mapper;

    public JsonView() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintWriter out = response.getWriter();
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        if (!model.isEmpty()) {
            doPrint(model.size() == 1 ? model.values().toArray()[0] : model, out);
        }

        if (model.isEmpty()) {
            out.print("");
        }

        out.flush();
    }

    private void doPrint(Object object, PrintWriter out) throws IOException {
        out.print(mapper.writeValueAsString(object));
    }
}

