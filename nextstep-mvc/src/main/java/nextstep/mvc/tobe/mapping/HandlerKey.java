package nextstep.mvc.tobe.mapping;

import nextstep.web.annotation.RequestMethod;
import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.Objects;

public class HandlerKey {
    private PathPattern pattern;
    private RequestMethod requestMethod;

    public HandlerKey(String url, RequestMethod requestMethod) {
        PathPatternParser pp = new PathPatternParser();
        pp.setMatchOptionalTrailingSeparator(true);
        pattern = pp.parse(url);

        this.requestMethod = requestMethod;
    }

    public boolean matches(String path, RequestMethod method) {
        return this.pattern.matches(toContainer(path)) && this.requestMethod.equals(method);
    }

    private PathContainer toContainer(String path) {
        if (Objects.isNull(path)) {
            return null;
        }
        return PathContainer.parsePath(path);
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
