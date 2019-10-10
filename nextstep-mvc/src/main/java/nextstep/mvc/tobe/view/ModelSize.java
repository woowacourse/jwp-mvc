package nextstep.mvc.tobe.view;

import nextstep.mvc.tobe.view.exception.InvalidModelSizeException;

import java.util.Arrays;
import java.util.function.Predicate;

public enum ModelSize {
    BLANK(size -> size == 0),
    ONE_SIZE(size -> size == 1),
    MANY_SIZE(size -> size >= 2);

    private final Predicate<Integer> sizeChecker;

    ModelSize(final Predicate<Integer> sizeChecker) {
        this.sizeChecker = sizeChecker;
    }

    public static ModelSize of(final int size) {
        return Arrays.stream(ModelSize.values())
                .filter(modelSize -> modelSize.sizeChecker.test(size))
                .findFirst()
                .orElseThrow(InvalidModelSizeException::new);
    }
}
