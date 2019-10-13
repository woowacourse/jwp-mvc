package nextstep.mvc.adapter.result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ExecutionResultAdapters {
    private static final Logger log = LoggerFactory.getLogger(ExecutionResultAdapters.class);

    private Map<Class<?>, ExecutionResultAdapter<?>> adapters;

    public ExecutionResultAdapters() {
        this.adapters = new HashMap<>();
    }

    public void add(ExecutionResultAdapter adapter) {
        Class<?> clazz = adapter.getType();

        checkDuplicateExecutionResultAdapter(clazz);

        adapters.put(clazz, adapter);
        log.info("Add initial ExecutionResultAdapter {}", adapter);
    }

    private void checkDuplicateExecutionResultAdapter(Class<?> clazz) {
        if (adapters.containsKey(clazz)) {
            throw new IllegalArgumentException("하나의 리턴값에 대한 처리 방식은 하나만 등록 가능합니다.");
        }
    }

    public ExecutionResultAdapter findAdapter(Object result) {
        ExecutionResultAdapter adapter = adapters.get(result.getClass());
        if (adapter == null) {
            throw new IllegalArgumentException("처리할 수 없는 형태입니다.");
        }
        return adapter;
    }
}
