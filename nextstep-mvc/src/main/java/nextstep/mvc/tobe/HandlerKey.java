package nextstep.mvc.tobe;

import nextstep.web.annotation.RequestMethod;

import java.util.regex.Pattern;

public class HandlerKey {
    private Pattern pattern;
    private String url;
    private RequestMethod requestMethod;

    public HandlerKey(String url, RequestMethod requestMethod) {
        this.url = url;
        this.requestMethod = requestMethod;
        this.pattern = Pattern.compile(url.replaceAll("\\{[a-zA-Z0-9]+\\}", "[a-zA-Z0-9]+"));
    }

    public boolean matchPattern(String requestUrl) {
        return pattern.matcher(requestUrl).find();
    }

    @Override
    public String toString() {
        return "HandlerKey [url=" + url + ", requestMethod=" + requestMethod + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((requestMethod == null) ? 0 : requestMethod.hashCode());
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
        if (requestMethod != other.requestMethod)
            return false;
        if (url == null) {
            return other.url == null;
        } else return url.equals(other.url);
    }
}