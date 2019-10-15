package nextstep.mvc.tobe.mapping;

import nextstep.utils.PathPatternUtils;
import nextstep.web.annotation.RequestMethod;
import org.springframework.web.util.pattern.PathPattern;

public class HandlerKey {
    private PathPattern pattern;
    private RequestMethod requestMethod;

    public HandlerKey(String url, RequestMethod requestMethod) {
        this.pattern = PathPatternUtils.parse(url);
        this.requestMethod = requestMethod;
    }

    public boolean matches(String path, RequestMethod method) {
        return this.pattern.matches(PathPatternUtils.toPathContainer(path))
                && this.requestMethod.equals(method);
    }

    @Override
    public String toString() {
        return "HandlerKey [pattern=" + pattern + ", requestMethod=" + requestMethod + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((requestMethod == null) ? 0 : requestMethod.hashCode());
        result = prime * result + ((pattern == null) ? 0 : pattern.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        HandlerKey other = (HandlerKey) obj;
        if (requestMethod != other.requestMethod)
            return false;
        if (pattern == null) {
            return other.pattern == null;
        } else return pattern.equals(other.pattern);
    }
}
