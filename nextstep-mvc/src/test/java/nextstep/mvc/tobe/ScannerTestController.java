package nextstep.mvc.tobe;

import nextstep.web.annotation.Controller;
import org.junit.jupiter.api.Test;

@Controller
public class ScannerTestController {
    private String test;

    public ScannerTestController(String test) {
        this.test = test;
    }
}
