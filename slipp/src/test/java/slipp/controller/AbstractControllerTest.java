package slipp.controller;

import nextstep.mvc.tobe.core.RequestMappingHandlerMapping;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import slipp.ManualLegacyHandlerMapping;

public abstract class AbstractControllerTest {
    protected MockHttpServletRequest request;
    protected MockHttpServletResponse response;
    protected RequestMappingHandlerMapping mappings;

    protected void initialize() {
        mappings = new RequestMappingHandlerMapping(new ManualLegacyHandlerMapping(), "slipp.controller");
        mappings.initialize();
    }
}
