package nextstep.mvc.tobe.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.RequestContext;
import nextstep.web.support.MediaType;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class JsonView implements View {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public void render(Map<String, ?> model, RequestContext requestContext) throws Exception {
        HttpServletResponse response = requestContext.getHttpServletResponse();

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        PrintWriter printWriter = response.getWriter();
        printWriter.write(convertData(model));
        printWriter.flush();
        printWriter.close();
    }

    private String convertData(Map<String, ?> model) throws JsonProcessingException {
        if(model.isEmpty()) {
            return StringUtils.EMPTY;
        }

        if (model.size() == 1) {
            Object value = model.values()
                    .iterator()
                    .next();
            return OBJECT_MAPPER.writeValueAsString(value);
        }

        return OBJECT_MAPPER.writeValueAsString(model);
    }
}
