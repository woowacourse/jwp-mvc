package nextstep.mvc.tobe.view;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.View;
import org.springframework.http.MediaType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class JsonView implements View {
    @Override
    public void render(Map<String, ?> model, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        resp.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        if(model.isEmpty()){
            return;
        }

        if(model.size() == 1 ){
            Object value = model.values()
                    .iterator()
                    .next();
            String parsedModel = objectMapper.writeValueAsString(value);
            resp.getWriter().write(parsedModel);
            return;
        }
        String parsedModel = objectMapper.writeValueAsString(model);
        resp.getWriter().write(parsedModel);
    }
}
