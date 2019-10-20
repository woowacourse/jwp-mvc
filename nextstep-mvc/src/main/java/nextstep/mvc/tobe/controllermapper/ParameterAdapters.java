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
            new RequestParameterAdapter(),
            new JavaBeenParameterAdapter()
    );

    public List<ParameterAdapter> getAdapters() {
        return new ArrayList<>(adepters);
    }
}
