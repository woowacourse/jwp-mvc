package nextstep.mvc.tobe.viewresolver;

public abstract class InternalResourceViewResolver implements ViewResolver {
    private final String prefix;
    private final String suffix;

    public InternalResourceViewResolver(final String prefix, final String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }
}
