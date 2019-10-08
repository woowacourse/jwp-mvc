package nextstep.mvc.adapter;

import nextstep.mvc.exception.ExecutionResultAdapterException;
import nextstep.mvc.tobe.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ExecutionResultAdapters {
    private static final Logger log = LoggerFactory.getLogger(ExecutionResultAdapters.class);

    private Map<Class, ExecutionResultAdapter> adapters;

    public void initialize() {
        log.info("Initialized ExecutionResultAdapters!");
        adapters = new HashMap<>();
        adapters.put(String.class, new StringExecutionResultAdapter());
        adapters.put(ModelAndView.class, new ModelAndViewExecutionResultAdapter());

        adapters.keySet().forEach(clazz -> log.info("Add initial adapter {} ExecutionResultAdapter", clazz));
    }

    public ExecutionResultAdapter findAdapter(Object result) {
        Class executionClass = result.getClass();
        Class clazz = adapters.keySet().stream()
                .filter(key -> key == executionClass)
                .findFirst()
                .orElseThrow(() -> new ExecutionResultAdapterException("처리할 수 없는 형태입니다."));
        return adapters.get(clazz);
    }
}
