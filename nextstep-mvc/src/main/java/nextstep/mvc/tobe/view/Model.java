package nextstep.mvc.tobe.view;

import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Map;

public class Model {
    private final Map<String, Object> model = Maps.newHashMap();

    public Object getObject(String name) {
        return model.get(name);
    }

    public void addObject(String name, Object object) {
        model.put(name, object);
    }

    public Map<String, Object> getModelMap() {
        return Collections.unmodifiableMap(model);
    }
}
