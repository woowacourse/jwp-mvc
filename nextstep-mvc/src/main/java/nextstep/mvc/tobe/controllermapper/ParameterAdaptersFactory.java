package nextstep.mvc.tobe.controllermapper;

import nextstep.mvc.tobe.controllermapper.adepter.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParameterAdaptersFactory {
    private static final List<ParameterAdapter> adepters = Arrays.asList(
            new IntegerParameterAdapter(),
            new LongParameterAdapter(),
            new StringParameterAdapter(),
            new ResponseParameterAdapter(),
            new RequestParameterAdapter(),
            new UserParameterAdapter()
    );

    public static List<ParameterAdapter> getAdepters() {
        return new ArrayList<>(adepters);
    }
}
