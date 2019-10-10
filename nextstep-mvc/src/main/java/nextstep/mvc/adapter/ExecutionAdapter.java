package nextstep.mvc.adapter;

import nextstep.mvc.Execution;
import nextstep.mvc.ModelAndView;
import nextstep.mvc.adapter.result.ExecutionResultAdapter;
import nextstep.mvc.adapter.result.ExecutionResultAdapters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class ExecutionAdapter {
    private final Class<?> type;

    final ExecutionResultAdapters executionResultAdapters = new ExecutionResultAdapters();

    ExecutionAdapter(Class type) {
        if (type == null) {
            throw new IllegalArgumentException("null로 생성할 수 없습니다.");
        }
        this.type = type;
    }

    public boolean matchClass(Object o) {
        return type.equals(o.getClass());
    }

    public ModelAndView execute(
            HttpServletRequest req,
            HttpServletResponse resp,
            Execution execution
    ) throws Exception {
        Object object = execution.execute(req, resp);
        ExecutionResultAdapter adapter = executionResultAdapters.findAdapter(object);
        return adapter.handle(req, resp, object);
    }
}
