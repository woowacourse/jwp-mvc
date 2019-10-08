package nextstep.mvc;

import nextstep.mvc.asis.Controller;

import java.net.http.HttpRequest;

public interface HandlerMapping {
    void initialize();

    Object getHandler(HttpRequest httpRequest);
}
