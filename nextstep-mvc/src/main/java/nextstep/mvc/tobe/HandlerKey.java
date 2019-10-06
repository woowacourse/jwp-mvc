package nextstep.mvc.tobe;

import nextstep.web.annotation.RequestMethod;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HandlerKey {
    private String url;
    private List<RequestMethod> requestMethods;

    HandlerKey(String url, RequestMethod[] requestMethods) {
        this.url = url;
        this.requestMethods = Arrays.asList(requestMethods);
    }

    public boolean isUrl(String url) {
        return this.url.equals(url);
    }

    public boolean containsMethodType(RequestMethod requestMethod) {
        if (requestMethods.contains(RequestMethod.ALL)) {
            return true;
        }
        return requestMethods.contains(requestMethod);
    }

    @Override
    public String toString() {
        return "HandlerKey{" +
                "url='" + url + '\'' +
                ", requestMethods=" + requestMethods +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HandlerKey that = (HandlerKey) o;
        return Objects.equals(url, that.url) &&
                Objects.equals(requestMethods, that.requestMethods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, requestMethods);
    }
}
