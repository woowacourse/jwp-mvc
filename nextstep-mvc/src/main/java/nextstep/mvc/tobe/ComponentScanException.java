package nextstep.mvc.tobe;

public class ComponentScanException extends RuntimeException {

    public ComponentScanException(Exception e) {
        super("Error while scanning components", e);
    }
}
