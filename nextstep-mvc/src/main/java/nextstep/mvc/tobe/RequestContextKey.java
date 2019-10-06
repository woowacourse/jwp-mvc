package nextstep.mvc.tobe;

import nextstep.mvc.tobe.view.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public enum RequestContextKey {
    REQUEST("request", HttpServletRequest.class),
    RESPONSE("response", HttpServletResponse.class),
    MODEL("model", Model.class);

    private String key;
    private Class<?> type;

    RequestContextKey(String key, Class<?> type) {
        this.key = key;
        this.type = type;
    }

    public String getKey(){
        return key;
    }

    public Class<?> getType(){
        return type;
    }

    public boolean hasType(Class<?> type){
        return Arrays.stream(values())
                .anyMatch(requestContextKey -> type.isAssignableFrom(this.type));
    }
}
