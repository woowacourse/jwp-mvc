package nextstep.study;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.mock.Car;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonTest {
    @DisplayName("requset의 바디를 가져옴")
    @Test
    void get_body_test() throws IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/json");
        String testWord = "test";
        String resultWord = "";
        request.setContent(testWord.getBytes());
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            resultWord = request.getReader().readLine();
        }
        assertThat(resultWord).isEqualTo(testWord);
    }

    @DisplayName("jackson을 통한 Car객체 파싱과 재조립")
    @Test
    void jackson_test() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Car car = new Car("yellow", "truck");
        String parsedToJson = objectMapper.writeValueAsString(car);
        Car reParsedCar = objectMapper.readValue(parsedToJson, new TypeReference<Car>() {
        });
        assertThat(reParsedCar).isEqualTo(car);
    }

}
