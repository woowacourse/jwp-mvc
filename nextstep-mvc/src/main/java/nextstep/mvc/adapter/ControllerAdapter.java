package nextstep.mvc.adapter;

import nextstep.mvc.ModelAndView;
import nextstep.mvc.adapter.result.ExecutionResultAdapter;
import nextstep.mvc.adapter.result.ExecutionResultAdapters;
import nextstep.mvc.adapter.result.StringExecutionResultAdapter;
import nextstep.mvc.asis.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerAdapter extends ExecutionAdapter<Controller> {
    private static final Logger log = LoggerFactory.getLogger(ControllerAdapter.class);

    public ControllerAdapter() {
        super(Controller.class);
        log.info("Initialize ExecutionResultAdapters!");
        adapters.add(new StringExecutionResultAdapter());
    }

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp, Controller execution) throws Exception {
        String viewName = execution.execute(req, resp);
        ExecutionResultAdapter adapter = adapters.findAdapter(viewName);
        return adapter.handle(req, resp, viewName);
    }
}
