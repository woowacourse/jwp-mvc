package nextstep.mvc.adapter;

import nextstep.mvc.Execution;
import nextstep.mvc.ModelAndView;
import nextstep.mvc.adapter.result.ExecutionResultAdapter;
import nextstep.mvc.adapter.result.ExecutionResultAdapters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class ExecutionAdapter {
    final ExecutionResultAdapters executionResultAdapters = new ExecutionResultAdapters();

    public abstract boolean matchClass(Object o);

    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp, Execution execution)
            throws Exception {
        Object result = execution.execute(req, resp);
        ExecutionResultAdapter adapter = executionResultAdapters.findAdapter(result);
        return adapter.handle(req, resp, result);
    }
}
