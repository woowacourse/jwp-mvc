package nextstep.mvc.adapter.result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ExecutionResultAdapters {
    private static final Logger log = LoggerFactory.getLogger(ExecutionResultAdapters.class);

    private List<ExecutionResultAdapter<?>> adapters;

    public ExecutionResultAdapters() {
        this.adapters = new ArrayList<>();
    }

    public void add(ExecutionResultAdapter adapter) {
        adapters.add(adapter);
        log.info("Add initial ExecutionResultAdapter {}", adapter);
    }

    public ExecutionResultAdapter findAdapter(Object result) {
        return adapters.stream()
                .filter(adapter -> adapter.matchClass(result))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("처리할 수 없는 형태입니다."));
    }
}
