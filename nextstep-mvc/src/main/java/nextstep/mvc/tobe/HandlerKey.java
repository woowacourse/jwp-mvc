package nextstep.mvc.tobe;

import nextstep.web.annotation.RequestMethod;

import java.util.Arrays;
import java.util.Objects;

public class HandlerKey {
    private String url;
    private RequestMethod[] requestMethods;

    public HandlerKey(String url, RequestMethod[] requestMethods) {
        this.url = url;
        this.requestMethods = requestMethods;
    }

    @Override
    public String toString() {
        return "HandlerKey [url=" + url + ", requestMethods=" + requestMethods + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HandlerKey that = (HandlerKey) o;
        return Objects.equals(url, that.url) &&
                Arrays.equals(requestMethods, that.requestMethods);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(url);
        result = 31 * result + Arrays.hashCode(requestMethods);
        return result;
    }

    /*@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((requestMethods == null) ? 0 : requestMethods.hashCode());
        result = prime * result + ((url == null) ? 0 : url.hashCode());
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
        if (requestMethods != other.requestMethods)
            return false;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;
        return true;
    }*/
}
