package nextstep.mvc.tobe;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;
import java.util.function.Supplier;

public class JsonView implements View {

    private final ObjectMapper mapper;

    public JsonView() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter writer = response.getWriter();
        writeOnCondition(writer, model::isEmpty, () -> "");
        writeOnCondition(writer, () -> model.size() == 1, () -> tryWriteValueAsString(getFirstValue(model)));
        writeOnCondition(writer, () -> model.size() > 1, () -> tryWriteValueAsString(model));
    }

    private void writeOnCondition(PrintWriter writer, Supplier<Boolean> condition, Supplier<String> valueSupplier) {
        if (condition.get()) {
            writer.print(valueSupplier.get());
        }
    }

    private Object getFirstValue(Map<String, ?> model) {
        return model.values().toArray()[0];
    }

    private String tryWriteValueAsString(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new ViewRenderException(e, this);
        }
    }
}
