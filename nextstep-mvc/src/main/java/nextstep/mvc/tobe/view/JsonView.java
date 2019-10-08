package nextstep.mvc.tobe.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import nextstep.utils.JsonUtils;
import nextstep.web.support.MediaType;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

public class JsonView implements View {
    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        PrintWriter printWriter = response.getWriter();
        printWriter.write(getResponseData(model));
        printWriter.flush();
        printWriter.close();
    }

    private String getResponseData(Map<String, ?> model) throws JsonProcessingException {
        return model.isEmpty() ? StringUtils.EMPTY : JsonUtils.toJson(modelToObject(model));
    }

    private Object modelToObject(Map<String, ?> model) {
        if (model.size() > 1) {
            return model;
        }

        return model.values()
                .iterator()
                .next();
    }
}
