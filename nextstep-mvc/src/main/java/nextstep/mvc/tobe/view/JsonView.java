package nextstep.mvc.tobe.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JsonView implements View {
    /*
    TODO
     * nextstep-mvc 모듈에 nextstep.mvc.tobe.JsonViewTest의 모든 테스트를 pass하도록 JsonView를 구현한다.
     *
     * 1. Request 객체에서 body 데이터를 읽어온다.
     * 2. Json 형식의 body 데이터를 Jackson 라이브러리를 활용해 Java Object로 변환한다.
     * 2-1. Jackson에 대한 학습 테스트를 추가해 사용법을 익힌다.
     * Intro to the Jackson ObjectMapper 문서 참고
     * 3. Java Object를 JSON 데이터로 변환하는 View 인터페이스에 대한 구현체를 추가한다.
     * 6. userId를 queryString으로 전달하는 경우 HttpServletRequest에서 값을 꺼내는 방법은 request.getParameter("userId")로 구현할 수 있다.
     */
    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.getWriter().write(writeContent(model));
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    }

    private String writeContent(final Map<String, ?> model) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        if (model.size() == 1) {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(model.values().toArray()[0]);
        }
        if (model.size() > 1) {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(model);
        }

        return "";
    }
}
