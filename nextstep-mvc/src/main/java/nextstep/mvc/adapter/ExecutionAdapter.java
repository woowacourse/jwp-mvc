package nextstep.mvc.adapter;

import nextstep.mvc.ModelAndView;
import nextstep.mvc.adapter.result.ExecutionResultAdapters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class ExecutionAdapter<T> {
    private final Class<T> type;

    protected final ExecutionResultAdapters adapters = new ExecutionResultAdapters();

    ExecutionAdapter(Class<T> type) {
        if (type == null) {
            throw new IllegalArgumentException("null로 생성할 수 없습니다.");
        }
        this.type = type;
    }

    public boolean matchClass(Object o) {
        return type.equals(o.getClass());
    }

    public abstract ModelAndView execute(
            HttpServletRequest req,
            HttpServletResponse resp,
            T execution
    ) throws Exception;
}
