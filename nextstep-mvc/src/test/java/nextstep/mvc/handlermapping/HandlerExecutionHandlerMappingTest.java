package nextstep.mvc.handlermapping;

import nextstep.web.annotation.RequestMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class HandlerExecutionHandlerMappingTest {

    @Test
    @DisplayName("존재하지 않는 url")
    void retrieve_notExistUrl() {
        // Arrange
        Map<HandlerKey, HandlerExecution> expectedMappings = new HashMap<>();

        // Act
        HandlerExecutionHandlerMapping mapping = createMapping(expectedMappings);

        // Assert
        HandlerKey notExistKey = HandlerKey.fromUrlAndRequestMethod("/notExist", RequestMethod.ALL);
        HttpServletRequest request = requestFilledWithUrlAndRequestMethod(notExistKey);
        assertThat(mapping.getHandler(request)).isEmpty();

    }

    @Test
    @DisplayName("핸들러 여러개등록, 전부 조회할 수 있는지")
    void registerSeveralHandlers_retrieveAllHandlers() {
        // Arrange
        Map<HandlerKey, HandlerExecution> expectedMappings = new HashMap<>() {{
            put(HandlerKey.fromUrlAndRequestMethod("/get", RequestMethod.GET), mock(HandlerExecution.class));
            put(HandlerKey.fromUrlAndRequestMethod("/put", RequestMethod.PUT), mock(HandlerExecution.class));
        }};

        // Act
        HandlerExecutionHandlerMapping mapping = createMapping(expectedMappings);

        // Assert
        for (HandlerKey handlerKey : expectedMappings.keySet()) {
            HttpServletRequest request = requestFilledWithUrlAndRequestMethod(handlerKey);

            assertThat(mapping.getHandler(request).get()).isEqualTo(expectedMappings.get(handlerKey));
        }
    }

    @Test
    @DisplayName("RequestMethod.ALL 은 모든 종류의 RequestMethod 들을 통해 조회 가능")
    void registerAllMethod_handleAllTypeOfMethod() {
        // Arrange
        String expectedUrl = "/url";
        HandlerExecution expectedExecution = mock(HandlerExecution.class);
        Map<HandlerKey, HandlerExecution> expectedMappings = new HashMap<>() {{
            put(HandlerKey.fromUrlAndRequestMethod(expectedUrl, RequestMethod.ALL), expectedExecution);
        }};

        // Act
        HandlerExecutionHandlerMapping mapping = createMapping(expectedMappings);
        mapping.initialize();

        // Assert
        for (RequestMethod method : RequestMethod.values()) {
            HandlerKey key = HandlerKey.fromUrlAndRequestMethod(expectedUrl, method);
            HttpServletRequest request = requestFilledWithUrlAndRequestMethod(key);

            assertThat(mapping.getHandler(request).get()).isEqualTo(expectedExecution);
        }
    }

    @Test
    @DisplayName("동일한 url 에 특정메소드와 ALL 이 같이 존재할 경우, 특정메소드 먼저 조회")
    void registerUrlWithMethodAndAll_handleSpecificMethod() {
        // Arrange
        String expectedUrl = "/url";
        RequestMethod expectedMethod = RequestMethod.POST;
        HandlerExecution expectedExecution = mock(HandlerExecution.class);
        Map<HandlerKey, HandlerExecution> expectedMappings = new HashMap<>() {{
            put(HandlerKey.fromUrlAndRequestMethod(expectedUrl, expectedMethod), expectedExecution);
            put(HandlerKey.fromUrlAndRequestMethod(expectedUrl, RequestMethod.ALL), mock(HandlerExecution.class));
        }};

        // Act
        HandlerExecutionHandlerMapping mapping = createMapping(expectedMappings);
        mapping.initialize();

        // Assert
        HandlerKey key = HandlerKey.fromUrlAndRequestMethod(expectedUrl, expectedMethod);
        HttpServletRequest request = requestFilledWithUrlAndRequestMethod(key);

        assertThat(mapping.getHandler(request).get()).isEqualTo(expectedExecution);
    }

    private HandlerExecutionHandlerMapping createMapping(Map<HandlerKey, HandlerExecution> expectedMappings) {
        HandlerExecutionHandlerMapping.Builder mappingBuilder = HandlerExecutionHandlerMapping.builder();
        for (HandlerKey handlerKey : expectedMappings.keySet()) {
            mappingBuilder.addKeyAndExecution(handlerKey, expectedMappings.get(handlerKey));
        }

        return mappingBuilder.build();
    }

    private HttpServletRequest requestFilledWithUrlAndRequestMethod(HandlerKey handlerKey) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI(handlerKey.getUrl());
        request.setMethod(handlerKey.getRequestMethod().toString());

        return request;
    }
}