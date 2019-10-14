package nextstep.mvc.tobe.mapping;

import com.google.common.collect.Maps;
import nextstep.web.annotation.RequestMethod;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HandlerKeyTest {
    private static final Logger log = LoggerFactory.getLogger(HandlerKeyTest.class);

    @Test
    void matches() {
        String url = "/users/{userId}/articles/{articleId}";
        HandlerKey key = new HandlerKey(url, RequestMethod.GET);
        log.debug("uri : {}", key);
        String requestUrl = "/users/pobi/articles/1";
        assertThat(key.matches(requestUrl, RequestMethod.GET)).isTrue();
    }
}