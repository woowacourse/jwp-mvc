package nextstep.web.annotation;

import org.junit.jupiter.api.Test;

public class RequestMappingTest {
    private static final String EXPECTED_VALUE = "value";
//    private static final RequestMethod[] EXPECTED_METHODS = {RequestMethod.GET, RequestMethod.POST};

    @Test
    void __() {

    }

    class classOfRequestMappingMethods {
        @RequestMapping
        public void empty() {

        }

        @RequestMapping(value = EXPECTED_VALUE)
        public void withValue() {

        }

        @RequestMapping(method = {RequestMethod.GET})
        public void withMethod() {

        }
    }
}
