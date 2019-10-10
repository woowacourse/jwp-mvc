package nextstep.mvc.adapter;

import nextstep.mvc.HandlerExecution;
import nextstep.mvc.adapter.result.ModelAndViewExecutionResultAdapter;
import nextstep.mvc.adapter.result.StringExecutionResultAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandlerExecutionAdapter extends ExecutionAdapter {
    private static final Logger log = LoggerFactory.getLogger(HandlerExecutionAdapter.class);

    public HandlerExecutionAdapter() {
        super(HandlerExecution.class);
        log.info("Initialize ExecutionResultAdapters!");
        executionResultAdapters.add(new StringExecutionResultAdapter());
        executionResultAdapters.add(new ModelAndViewExecutionResultAdapter());
    }
}
