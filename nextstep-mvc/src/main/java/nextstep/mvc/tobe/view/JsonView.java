package nextstep.mvc.tobe.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.utils.Objects;
import nextstep.utils.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class JsonView implements View {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private Object object;

    public JsonView() {
    }

    public JsonView(final Object object) {
        this.object = object;
    }

    @Override
    public void render(final Map<String, ?> model, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        if (object instanceof ResponseEntity) {
            final ResponseEntity responseEntity = (ResponseEntity) this.object;
            setStatusLine(responseEntity, response);
            setHeaders(responseEntity, response);
            setBody(responseEntity.getBody(), model, response);
            return;
        }
        setBody(object, model, response);
    }

    private void setStatusLine(final ResponseEntity responseEntity, final HttpServletResponse response) {
        response.setStatus(responseEntity.getStatusCodeValue());
    }

    private void setHeaders(final ResponseEntity responseEntity, final HttpServletResponse response) {
        final HttpHeaders headers = responseEntity.getHeaders();
        // todo First 말고 전체 리스트 담아주기 ( ResponseEntity, HttpHeaders 직접 구현했을 때)
        headers.forEach((key, value) -> response.addHeader(key, headers.getFirst(key)));
    }

    private void setBody(final Object body, final Map<String, ?> model, final HttpServletResponse response) throws IOException {
        final PrintWriter writer = response.getWriter();

        if (Objects.isNotNull(body)) {
            writer.println(OBJECT_MAPPER.writeValueAsString(body));
        }
        writer.println(convert(model));
        writer.flush();
    }

    private Object convert(final Map<String, ?> model) throws JsonProcessingException {
        if (Objects.isNull(model) || model.size() == 0) {
            return StringUtils.EMPTY;
        }

        if (model.size() == 1) {
            return OBJECT_MAPPER.writeValueAsString(model.values().toArray()[0]);
        }

        return OBJECT_MAPPER.writeValueAsString(model);
    }
}
