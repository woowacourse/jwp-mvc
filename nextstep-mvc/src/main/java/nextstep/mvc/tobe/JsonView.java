package nextstep.mvc.tobe;

import nextstep.web.support.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JsonView implements View {
    private static final Logger log = LoggerFactory.getLogger(JsonView.class);

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.addHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
    }

    @Override
    public String getName() {
        return null;
    }
}
