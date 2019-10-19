package nextstep.mvc.handleradapter;

import nextstep.mvc.BadHttpRequestException;
import nextstep.mvc.NotSupportedHandlerException;
import nextstep.mvc.handlermapping.HandlerMapping;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SupportedHandlerFactoryTest {
    @Test
    void create() {
    }

    @Test
    @DisplayName("처리하지 않는 요청, BadHttpRequestException 발생")
    void create_receiveBadRequest() {
        // Arrange
        HandlerMapping emptyMapping = mock(HandlerMapping.class);
        given(emptyMapping.getHandler(any(HttpServletRequest.class))).willReturn(Optional.empty());
        HttpServletRequest badRequest = mock(HttpServletRequest.class);

        SupportedHandlerAdapterFactory factory = SupportedHandlerAdapterFactory.from(
                emptyMapping,
                mock(HandlerAdapterWrappers.class));

        // Act & Assert
        assertThrows(BadHttpRequestException.class, () -> factory.create(badRequest));
    }

    @Test
    @DisplayName("지원하지 않는 요청, NotSupportedHandlerException 발생")
    void create_receiveRequestOfNotSupportedHandler() {
        // Arrange
        HttpServletRequest requestToNotSupportedHandler = mock(HttpServletRequest.class);
        HandlerMapping mappingToNotSupportedHandler = mock(HandlerMapping.class);
        given(mappingToNotSupportedHandler.getHandler(requestToNotSupportedHandler))
                .willReturn(Optional.of(new NotSupportedHandler()));

        HandlerAdapterWrappers wrappers = mock(HandlerAdapterWrappers.class);
        given(wrappers.wrap(any())).willReturn(Optional.empty());

        SupportedHandlerAdapterFactory factory = SupportedHandlerAdapterFactory.from(
                mappingToNotSupportedHandler, wrappers);

        // Act & Assert
        assertThrows(NotSupportedHandlerException.class, () -> factory.create(requestToNotSupportedHandler));
    }

    private class NotSupportedHandler {
    }

    @Test
    @DisplayName("올바른 요청, 올바른 Handler 반환")
    void create_receiveCorrectRequest_returnCorrectAdapter() {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HandlerMapping mappingToNotSupportedHandler = mock(HandlerMapping.class);
        given(mappingToNotSupportedHandler.getHandler(request))
                .willReturn(Optional.of(new SupportedHandler()));

        Handler expectedAdapter = mock(Handler.class);
        HandlerAdapterWrappers wrappers = mock(HandlerAdapterWrappers.class);
        given(wrappers.wrap(any(SupportedHandler.class))).willReturn(Optional.of(expectedAdapter));

        SupportedHandlerAdapterFactory factory = SupportedHandlerAdapterFactory.from(
                mappingToNotSupportedHandler, wrappers);

        // Act & Assert
        assertThat(factory.create(request)).isEqualTo(expectedAdapter);
    }

    private class SupportedHandler {
    }
}