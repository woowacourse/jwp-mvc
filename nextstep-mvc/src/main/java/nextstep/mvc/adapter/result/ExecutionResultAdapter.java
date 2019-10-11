package nextstep.mvc.adapter.result;

import nextstep.mvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class ExecutionResultAdapter<T> {
    private final Class<T> type;

    ExecutionResultAdapter(Class<T> type) {
        if (type == null) {
            throw new IllegalArgumentException("null로 생성할 수 없습니다.");
        }
        this.type = type;
    }

    boolean matchType(ExecutionResultAdapter adapter) {
        return this.type.equals(adapter.type);
    }

    boolean matchClass(Object o) {
        return type.equals(o.getClass());
    }

    public abstract ModelAndView handle(HttpServletRequest request, HttpServletResponse response, T result);

    @Override
    public String toString() {
        return "ExecutionResultAdapter{" +
                "type=" + type +
                '}';
    }
}
