package nextstep.mvc.tobe;

import nextstep.web.annotation.RequestMethod;

import java.util.Objects;

class HandlerKey {
    private String url;
    private RequestMethod requestMethod;

    HandlerKey(final String url, final RequestMethod requestMethod) {
        this.url = url;
        this.requestMethod = requestMethod;
    }

    @Override
    public boolean equals(final Object another) {
        if (this == another) return true;
        if (another == null || getClass() != another.getClass()) return false;
        final HandlerKey key = (HandlerKey) another;
        return Objects.equals(url, key.url) &&
                requestMethod == key.requestMethod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, requestMethod);
    }

    @Override
    public String toString() {
        return "HandlerKey {" +
                "url: \"" + url + "\"" +
                ", requestMethod: " + requestMethod +
                "}";
    }
}
