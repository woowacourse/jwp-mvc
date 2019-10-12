package nextstep.mvc.tobe.controllermapper;

import nextstep.mvc.tobe.controllermapper.adepter.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParameterAdeptersFactory {
    private static final List<ParameterAdepter> adepters = Arrays.asList(
            new IntegerParameterAdepter(),
            new LongParameterAdepter(),
            new StringParameterAdepter(),
            new ResponseParameterAdepter(),
            new RequestParameterAdepter(),
            new UserParameterAdepter()
    );

    public static List<ParameterAdepter> getAdepters() {
        return new ArrayList<>(adepters);
    }
}
