package nextstep.mvc.adapter;

import nextstep.mvc.adapter.result.StringExecutionResultAdapter;
import nextstep.mvc.asis.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerAdapter extends ExecutionAdapter {
    private static final Logger log = LoggerFactory.getLogger(ControllerAdapter.class);

    public ControllerAdapter() {
        super(Controller.class);
        log.info("Initialize ExecutionResultAdapters!");
        executionResultAdapters.add(new StringExecutionResultAdapter());
    }
}
