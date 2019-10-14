package nextstep.mvc.tobe.controllermapper;

import com.google.common.collect.Lists;
import nextstep.mvc.tobe.controllermapper.adepter.*;

import java.util.ArrayList;
import java.util.List;

public class ParameterAdapters {
    private final ArrayList<ParameterAdapter> adepters = Lists.newArrayList(
            new IntegerParameterAdapter(),
            new LongParameterAdapter(),
            new StringParameterAdapter(),
            new ResponseParameterAdapter(),
            new RequestParameterAdapter()
    );

    private Object[] basePackage;

    public ParameterAdapters(Object... basePackage) {
        this.basePackage = basePackage;
        initialize();
    }

    private void initialize() {
        ParameterResolverScanner parameterResolverScanner = new ParameterResolverScanner(basePackage);
        adepters.addAll(parameterResolverScanner.getParameterAdapter());
    }

    public List<ParameterAdapter> getAdapters() {
        return new ArrayList<>(adepters);
    }
}
