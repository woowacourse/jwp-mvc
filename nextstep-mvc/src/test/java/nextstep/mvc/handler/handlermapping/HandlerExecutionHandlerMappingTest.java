package nextstep.mvc.handler.handlermapping;

import nextstep.web.annotation.RequestMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class HandlerExecutionHandlerMappingTest {
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