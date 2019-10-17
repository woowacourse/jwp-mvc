package nextstep.mvc.tobe;

import nextstep.web.annotation.RequestMethod;

import java.util.Objects;

public class Mapping {
    private final String url;
    private final RequestMethod requestMethod;

    public Mapping(String url, RequestMethod requestMethod) {
        this.url = url;
        this.requestMethod = requestMethod;
    }

    @Override
    public String toString() {
        return "Mapping [url=" + url + ", requestMethod=" + requestMethod + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mapping)) {
            return false;
        }
        final Mapping rhs = (Mapping) o;
        return Objects.equals(this.url, rhs.url) &&
                this.requestMethod == rhs.requestMethod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.url, this.requestMethod);
    }
}