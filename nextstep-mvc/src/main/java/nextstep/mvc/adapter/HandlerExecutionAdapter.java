package nextstep.mvc.adapter;

import nextstep.mvc.HandlerExecution;
import nextstep.mvc.ModelAndView;
import nextstep.mvc.adapter.result.ExecutionResultAdapter;
import nextstep.mvc.adapter.result.ModelAndViewExecutionResultAdapter;
import nextstep.mvc.adapter.result.StringExecutionResultAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerExecutionAdapter extends ExecutionAdapter<HandlerExecution> {
    private static final Logger log = LoggerFactory.getLogger(HandlerExecutionAdapter.class);

    public HandlerExecutionAdapter() {
        super(HandlerExecution.class);
        log.info("Initialize ExecutionResultAdapters!");
        adapters.add(new StringExecutionResultAdapter());
        adapters.add(new ModelAndViewExecutionResultAdapter());
    }

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp, HandlerExecution execution) throws Exception {
        Object o = execution.execute(req, resp);
        ExecutionResultAdapter adapter = adapters.findAdapter(o);
        return adapter.handle(req, resp, o);
    }
}
